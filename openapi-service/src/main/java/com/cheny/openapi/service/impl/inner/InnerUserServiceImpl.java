package com.cheny.openapi.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cheny.openapi.common.api.ErrorCode;
import com.cheny.openapi.common.exception.BusinessException;
import com.cheny.openapi.mapper.UserMapper;
import com.cheny.openapi.common.model.entity.User;
import com.cheny.openapi.common.service.InnerUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * @author chen_y
 * @date 2024-07-21 0:33
 */
@DubboService
public class InnerUserServiceImpl implements InnerUserService {
    @Resource
    private UserMapper userMapper;

    /**
     * 实现接口中的 getInvokeUser 方法，用于根据密钥获取内部用户信息。
     *
     * @param accessKey 密钥
     * @return 内部用户信息，如果找不到匹配的用户则返回 null
     * @throws BusinessException 参数错误时抛出业务异常
     */
    @Override
    public User getInvokeUser(String accessKey) {
        // 参数校验
        if (StringUtils.isAnyBlank(accessKey)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 创建查询条件包装器
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("accessKey", accessKey);
        return userMapper.selectOne(queryWrapper);
    }
}
