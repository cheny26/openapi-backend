package com.cheny.openapi.common.service;


import com.cheny.openapi.common.model.entity.InterfaceInfo;

import java.util.List;

/**
 * 接口服务
 *
 */
public interface InnerInterfaceInfoService {

    /**
     * 根据接口名字与方法获取接口信息
     * @param name
     * @param method
     * @return
     */
    InterfaceInfo getInterfaceInfo(String name,String method);

    List<InterfaceInfo> getAllInterfaceInfos();
}
