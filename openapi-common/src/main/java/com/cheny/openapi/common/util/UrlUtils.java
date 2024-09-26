package com.cheny.openapi.common.util;

import java.net.URI;
import java.net.URISyntaxException;

public class UrlUtils {

    /**
     * 从完整的 URL 中提取出基础 URI (包含协议、域名和端口)
     * @param url 完整的 URL
     * @return 基础 URI
     * @throws URISyntaxException 如果 URL 格式错误，抛出 URISyntaxException
     */
    public static String getBaseUri(String url) throws URISyntaxException {
        URI uri = new URI(url);
        // 提取协议、主机名和端口
        String baseUri = uri.getScheme() + "://" + uri.getHost();
        
        // 如果端口号存在且不是默认端口，添加端口
        if (uri.getPort() != -1) {
            baseUri += ":" + uri.getPort();
        }
        return baseUri;
    }

    /**
     * 从完整的 URL 中提取出路径部分
     * @param url 完整的 URL
     * @return 路径部分
     * @throws URISyntaxException 如果 URL 格式错误，抛出 URISyntaxException
     */
    public static String getPath(String url) throws URISyntaxException {
        URI uri = new URI(url);
        // 提取路径部分
        return uri.getPath();
    }
    
}
