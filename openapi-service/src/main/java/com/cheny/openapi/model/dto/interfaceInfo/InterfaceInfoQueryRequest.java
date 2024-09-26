package com.cheny.openapi.model.dto.interfaceInfo;

import com.cheny.openapi.common.api.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author chen_y
 * @date 2024-06-21 23:17
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class InterfaceInfoQueryRequest extends PageRequest implements Serializable {
    /**
     * id
     */
    private Long id;

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

}
