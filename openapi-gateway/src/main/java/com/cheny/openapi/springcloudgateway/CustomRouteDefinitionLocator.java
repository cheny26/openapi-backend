package com.cheny.openapi.springcloudgateway;

import com.cheny.openapi.common.model.entity.InterfaceInfo;
import com.cheny.openapi.common.service.InnerInterfaceInfoService;
import com.cheny.openapi.common.util.UrlUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomRouteDefinitionLocator implements RouteDefinitionLocator {

    @DubboReference
    private InnerInterfaceInfoService innerInterfaceInfoService;

    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        // 从数据库或缓存中获取所有的 API 请求信息
        List<InterfaceInfo> allInterfaceInfos = innerInterfaceInfoService.getAllInterfaceInfos();

        // 转换为 RouteDefinition
        List<RouteDefinition> routeDefinitions = allInterfaceInfos.stream().map(apiRequest -> {
            RouteDefinition routeDefinition = new RouteDefinition();
            routeDefinition.setId("custom-route-" + apiRequest.getId());

            // 动态设置目标 URI
            //routeDefinition.setUri(URI.create(apiRequest.getProtocol() + "://" + apiRequest.getTargetIp() + ":" + apiRequest.getTargetPort()));
            try {
                routeDefinition.setUri(URI.create(UrlUtils.getBaseUri(apiRequest.getUrl())));
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
            // 配置路由规则，如根据路径匹配
            PredicateDefinition predicate = new PredicateDefinition();
            predicate.setName("Path");
            try {
                predicate.addArg("pattern", UrlUtils.getPath(apiRequest.getUrl()));
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }

            routeDefinition.setPredicates(Collections.singletonList(predicate));

            return routeDefinition;
        }).collect(Collectors.toList());

        return Flux.fromIterable(routeDefinitions);
    }
}
