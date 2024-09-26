package com.cheny.openapi.sdk.constant;

public enum HttpMethod {
    GET("get"),
    POST("post");
    private final String methodName;

    HttpMethod(String methodName){
        this.methodName = methodName;
    }

    public boolean equals(String methodName) {
        return this.methodName.equals(methodName);
    }
}
