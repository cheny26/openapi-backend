package com.cheny.openapi.common.service;


import com.cheny.openapi.common.model.entity.User;

/**
 * 用户服务
 *
 */
public interface InnerUserService {

    /**
     * 根据accessKey获取调用接口用户的信息
     * @param accessKey accessKey
     * @return 返回用户信息
     */
    User getInvokeUser(String accessKey);
}
