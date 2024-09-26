package com.cheny.openapi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cheny.openapi.common.model.entity.UserInterfaceInfo;

import java.util.List;

/**
* @author chen
* @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Mapper
* @createDate 2024-07-01 17:48:20
* @Entity com.cheny.WuJieAPI.model.entity.UserInterfaceInfo
*/
public interface UserInterfaceInfoMapper extends BaseMapper<UserInterfaceInfo> {
    List<UserInterfaceInfo> listTopInvokeInterfaceInfo(int limit);
}




