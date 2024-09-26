package com.cheny.openapi.common.service;


/**
 * 用户接口信息服务
 *
 */
public interface InnerUserInterfaceInfoService {

    /**
     * 调用接口统计
     * @param interfaceInfoId
     * @param userId
     * @return
     */
    Boolean invokeCount(long interfaceInfoId, long userId);
}
