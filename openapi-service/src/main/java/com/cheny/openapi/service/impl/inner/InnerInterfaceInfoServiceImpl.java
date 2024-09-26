package com.cheny.openapi.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cheny.openapi.common.api.ErrorCode;
import com.cheny.openapi.common.exception.BusinessException;
import com.cheny.openapi.mapper.InterfaceInfoMapper;
import com.cheny.openapi.common.model.entity.InterfaceInfo;
import com.cheny.openapi.common.service.InnerInterfaceInfoService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author chen_y
 * @date 2024-07-21 0:33
 */
@DubboService
public class InnerInterfaceInfoServiceImpl implements InnerInterfaceInfoService {
    @Resource
    private InterfaceInfoMapper interfaceInfoMapper;
    /**
     * 实现接口中的 getInterfaceInfo 方法，用于根据URL和请求方法获取内部接口信息。
     *
     * @param url    请求URL
     * @param method 请求方法
     * @return 内部接口信息，如果找不到匹配的接口则返回 null
     * @throws BusinessException 参数错误时抛出业务异常
     */
    @Override
    public InterfaceInfo getInterfaceInfo(String url, String method) {
        // 参数校验
        if (StringUtils.isAnyBlank(url, method)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 创建查询条件包装器
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("url", url);
        queryWrapper.eq("method", method);
        return interfaceInfoMapper.selectOne(queryWrapper);
    }

    @Override
    public List<InterfaceInfo> getAllInterfaceInfos() {
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
        return  interfaceInfoMapper.selectList(queryWrapper);
    }

}