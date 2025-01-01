package com.cheny.openapi.model.dto.interfaceInfo;

import lombok.Data;

import java.io.Serializable;
/**
 * @author chen_y
 * @date 2024-06-21 23:11
 */
@Data
public class InterfaceInfoAddRequest implements Serializable {

    /**
     * 接口名称
     */
    private String name;

    /**
     * 接口描述
     */
    private String description;

    /**
     * 接口地址
     */
    private String url;

    /**
     * 请求类型
     */
    private String method;

    /**
     * 请求头
     */
    private String requestHeader;

    /**
     * 请求参数
     */
    private String requestParams;

    /**
     * 响应头
     */
    private String responseHeader;

    /**
     * 消耗积分
     */
    private Integer pointsCost;


    /**
     * 创建人姓名
     */
    private String userName;


    private static final long serialVersionUID = 1L;
}
