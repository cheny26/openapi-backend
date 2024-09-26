package com.cheny.openapi.sdk;

import com.cheny.openapi.sdk.client.APIClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author chen_y
 * @date 2024-06-29 18:06
 */
@Configuration
@ConfigurationProperties("api.client")
@Data
// @ComponentScan 注解用于自动扫描组件，使得 Spring 能够自动注册相应的 Bean
@ComponentScan
public class APIClientConfig {

    private String accessKey;

    private String secretKey;

    @Bean
    public APIClient apiClient(){
        return new APIClient(accessKey,secretKey);
    }
}