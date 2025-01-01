package com.cheny.openapi.service.impl.inner;

import com.cheny.openapi.service.UserInterfaceInfoService;
import com.cheny.openapi.common.service.InnerUserInterfaceInfoService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * @author chen_y
 * @date 2024-07-21 0:33
 */
@DubboService
public class InnerUserInterfaceInfoServiceImpl implements InnerUserInterfaceInfoService {
    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;
    @Override
    public Boolean invokeCount(long interfaceInfoId, long userId,int pointCost) {
        return userInterfaceInfoService.invokeCount(interfaceInfoId,userId,pointCost);
    }
}
