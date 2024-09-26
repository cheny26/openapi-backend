package com.cheny.openapi.springcloudgateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RouteController {

    private final RouteLocator routeLocator;
    private final RouteDefinitionLocator routeDefinitionLocator;

    public RouteController(RouteLocator routeLocator, RouteDefinitionLocator routeDefinitionLocator) {
        this.routeLocator = routeLocator;
        this.routeDefinitionLocator = routeDefinitionLocator;
    }

    // 获取所有已加载的路由
    @GetMapping("/routes")
    public Object getRoutes() {
        return routeLocator.getRoutes();
    }

    // 获取所有路由定义，包括动态添加的
    @GetMapping("/routeDefinitions")
    public Object getRouteDefinitions() {
        return routeDefinitionLocator.getRouteDefinitions();
    }
}
