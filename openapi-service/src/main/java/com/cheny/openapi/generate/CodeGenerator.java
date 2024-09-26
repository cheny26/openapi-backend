package com.cheny.openapi.generate;

import com.cheny.openapi.common.model.entity.User;

/**
 * 代码生成器
 *
 */
public class CodeGenerator {

    /**
     * 用法：追加process(数据类.class, "数据别名");
     */
    public static void main(String[] args) {
        // 代码生成处理器
        new GenerateProcessor()
                // 生成项目路径
                .packageName("com.cheny.WuJieAPI")
                // 排除字段策略
                .exclusionStrategy("serialVersionUID", "isDelete")
                .process(User.class, "用户");
        // ..继续追加process(数据类.class, "数据别名");
    }
}
