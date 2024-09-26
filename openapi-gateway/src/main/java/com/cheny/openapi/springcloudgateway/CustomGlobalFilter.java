package com.cheny.openapi.springcloudgateway;

import com.cheny.openapi.common.model.entity.InterfaceInfo;
import com.cheny.openapi.common.model.entity.User;
import com.cheny.openapi.common.service.InnerInterfaceInfoService;
import com.cheny.openapi.common.service.InnerUserInterfaceInfoService;
import com.cheny.openapi.common.service.InnerUserService;
import com.cheny.openapi.sdk.utils.GeneratorUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 * @author chen_y
 * @date 2024-07-19 23:16
 */
@Slf4j
@Component
public class CustomGlobalFilter implements GlobalFilter, Ordered {
    @DubboReference
    private InnerUserService innerUserService;

    @DubboReference
    private InnerInterfaceInfoService innerInterfaceInfoService;

    @DubboReference
    private InnerUserInterfaceInfoService innerUserInterfaceInfoService;
    private final static String BASEURL="http://localhost:8081";
    //禁止访问的黑名单
    private static final List<String> BLACKLIST = Collections.singletonList("0.0.0.1");
    //存放已经使用过的随机数
    HashSet<String> sets = new HashSet<>();


    @Bean
    public GlobalFilter customFilter() {
        return new CustomGlobalFilter();
    }


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //请求日志
        ServerHttpRequest request = exchange.getRequest();
        log.info("请求ID为" + request.getId());
        log.info("LocalAddress为" + request.getLocalAddress().getHostString());
        log.info("请求路径为" + request.getPath());
        log.info("RemoteAddress为" + request.getRemoteAddress());
        log.info("请求参数为" + request.getQueryParams());
        log.info("请求URI为" + request.getURI());
        //拿到响应对象
        ServerHttpResponse response = exchange.getResponse();
        //访问控制
        if (BLACKLIST.contains(request.getLocalAddress().getHostString())) {
            response.setStatusCode(HttpStatus.FORBIDDEN);
            return response.setComplete();
        }
        //鉴权
        // 从请求头中获取参数
        HttpHeaders headers = request.getHeaders();
        String accessKey = headers.getFirst("accessKey");
        String nonce = headers.getFirst("nonce");
        String timestamp = headers.getFirst("timestamp");
        String sign = headers.getFirst("sign");
        String body = headers.getFirst("body");
        User invokeUser = innerUserService.getInvokeUser(accessKey);
        if (invokeUser == null) {
            response.setStatusCode(HttpStatus.FORBIDDEN);
            return response.setComplete();
        }
        String secretKey = invokeUser.getSecretKey();
        //判断是否存在该accessKey
        if (secretKey == null) {
            response.setStatusCode(HttpStatus.FORBIDDEN);
            return response.setComplete();
        }
        //判断随机数是否在最近五分钟内使用过
        if (sets.contains(nonce) && timestamp!=null && Math.abs(System.currentTimeMillis() - Long.parseLong(timestamp)) > 5 * 60 * 1000) {
            response.setStatusCode(HttpStatus.FORBIDDEN);
            return response.setComplete();
        }
        //TODO 使用缓存优化
        sets.add(nonce);
        String s = GeneratorUtils.generateSign(body, secretKey);
        if (!s.equals(sign)) {
            response.setStatusCode(HttpStatus.FORBIDDEN);
            return response.setComplete();
        }
        //TODO 优化是否在数据库中存在的逻辑
        InterfaceInfo interfaceInfo = innerInterfaceInfoService.getInterfaceInfo(BASEURL+request.getPath().value(), request.getMethod().toString());
        if (interfaceInfo == null) {
            response.setStatusCode(HttpStatus.FORBIDDEN);
            return response.setComplete();
        }
        return handleResponse(exchange, chain, interfaceInfo.getId(), invokeUser.getId());
    }

    //设置过滤器执行等级
    @Override
    public int getOrder() {
        return -1;
    }

    public Mono<Void> handleResponse(ServerWebExchange exchange, GatewayFilterChain chain, long interFaceInfoId, long userId) {
        try {
            // 获取原始的响应对象
            ServerHttpResponse originalResponse = exchange.getResponse();
            // 获取数据缓冲工厂
            DataBufferFactory bufferFactory = originalResponse.bufferFactory();
            // 获取响应的状态码
            HttpStatus statusCode = originalResponse.getStatusCode();
            // 判断状态码是否为200 OK
            if (statusCode == HttpStatus.OK) {
                // 创建一个装饰后的响应对象(开始穿装备，增强能力)
                ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
                    // 重写writeWith方法，用于处理响应体的数据
                    // 这段方法就是只要当我们的模拟接口调用完成之后,等它返回结果，
                    // 就会调用writeWith方法,我们就能根据响应结果做一些自己的处理
                    @Override
                    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                        // 判断响应体是否是Flux类型
                        if (body instanceof Flux) {
                            //调用成功，接口调用次数+1
                            try {
                                innerUserInterfaceInfoService.invokeCount(interFaceInfoId, userId);
                            } catch (Exception e) {
                                log.error("invoke error", e);
                            }
                            Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                            // 返回一个处理后的响应体
                            // (这里就理解为它在拼接字符串,它把缓冲区的数据取出来，一点一点拼接好)
                            return super.writeWith(fluxBody.map(dataBuffer -> {
                                // 读取响应体的内容并转换为字节数组
                                byte[] content = new byte[dataBuffer.readableByteCount()];
                                dataBuffer.read(content);
                                DataBufferUtils.release(dataBuffer);//释放掉内存
                                String data = new String(content, StandardCharsets.UTF_8);//data
                                log.info("响应结果为" + data);
                                // 将处理后的内容重新包装成DataBuffer并返回
                                return bufferFactory.wrap(content);
                            }));
                        } else {
                            //调用失败，返回错误码
                            log.error("<--- {} 响应code异常", getStatusCode());
                        }
                        return super.writeWith(body);
                    }
                };
                // 对于200 OK的请求,将装饰后的响应对象传递给下一个过滤器链,并继续处理(设置respsonse对象为装饰过的)
                return chain.filter(exchange.mutate().response(decoratedResponse).build());
            }
            // 对于非200 OK的请求，直接返回，进行降级处理
            return chain.filter(exchange);
        } catch (Exception e) {
            // 处理异常情况，记录错误日志
            log.error("gateway log exception.\n" + e);
            return chain.filter(exchange);
        }
    }

}
