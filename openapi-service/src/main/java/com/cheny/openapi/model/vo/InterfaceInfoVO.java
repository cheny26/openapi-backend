package com.cheny.openapi.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chen_y
 * @date 2024-06-21 23:16
 */
@Data
public class InterfaceInfoVO implements Serializable {

    /**
     * 接口名称
     */
    private String name;

    /**
     * 调用次数
     */
    private Integer totalNum;
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
     * 请求头
     */
    private String requestParams;

    /**
     * 响应头
     */
    private String responseHeader;

    /**
     * 接口状态（0 关闭 1 开启）
     */
    private Integer status;

    /**
     * 创建人id
     */
    private Long userId;

    /**
     * 创建人姓名
     */
    private String userName;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

}
