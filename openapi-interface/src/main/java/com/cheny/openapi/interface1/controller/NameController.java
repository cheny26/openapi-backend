package com.cheny.openapi.interface1.controller;


import com.cheny.openapi.common.api.BaseResponse;
import com.cheny.openapi.common.api.ResultUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * 名称 API
 *
 */
@RestController
@RequestMapping("/name")
public class NameController {

    @PostMapping("/user")
    public String getUserNameByPost(@RequestBody Map<String,String> body) {
        String name = body.get("name");
        String age = body.get("age");
        return "你输入的名字是和年龄分别为" + name+"-"+age;
    }

    @GetMapping("/getName")
    public BaseResponse<String> getName(String name,String age){
        String s="你输入的名字是和年龄分别为" + name+"-"+age;;
        return ResultUtils.success(s);
    }
}