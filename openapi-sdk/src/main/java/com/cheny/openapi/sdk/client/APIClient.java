package com.cheny.openapi.sdk.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import com.cheny.openapi.common.util.UrlUtils;
import com.cheny.openapi.sdk.utils.GeneratorUtils;

import java.util.HashMap;

/**
 * @author chen_y
 * @date 2024-06-28 23:08
 */
public class APIClient {
    private static final String baseUrl="http://localhost:8082";
    private final String  accessKey;
    private final String secretKey;

    public APIClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public  String invokeByWeb(String method, String path, JSONObject params, JSONObject data)  throws Exception{
       String  newPath= UrlUtils.getPath(path);
        if(method.equals("get"))
            return get(newPath,params);
        else
            return post(newPath,data);
    }

    public String get(String path, JSONObject params){
        return HttpRequest.get(baseUrl + path).form(params).addHeaders(generateMap(params.toString())).execute().body();
    }

    public String post(String path,JSONObject body){
        return HttpRequest.post(baseUrl+path).body(body.toString()).addHeaders(generateMap(body.toString())).execute().body();
    }

    public HashMap<String,String> generateMap(String body){
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put("accessKey", accessKey);
        // 生成随机数(生成一个包含100个随机数字的字符串)
        hashMap.put("nonce", RandomUtil.randomNumbers(4));
        // 当前时间戳
        hashMap.put("timestamp", String.valueOf(System.currentTimeMillis()));
        // 请求体内容
        hashMap.put("body", body);
        // 生成签名
        hashMap.put("sign", GeneratorUtils.generateSign(body,secretKey));
        return hashMap;
    }
}
