package com.cheny.openapi.model.enums;

import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 接口状态枚举
 */
public enum InterfaceStatus {

        ONLINE("上线", 1),
        OFFLINE("下线",0);

        private final String text;

        private final Integer value;

    InterfaceStatus(String text, Integer value) {
            this.text = text;
            this.value = value;
        }

        /**
         * 获取值列表
         *
         * @return
         */
        public static List<Integer> getValues() {
            return Arrays.stream(InterfaceStatus.values()).map(item -> item.value).collect(Collectors.toList());
        }

        /**
         * 根据 value 获取枚举
         *
         * @param value
         * @return
         */
        public static  InterfaceStatus getEnumByValue(Integer value) {
            if (ObjectUtils.isEmpty(value)) {
                return null;
            }
            for (InterfaceStatus anEnum:InterfaceStatus.values()) {
                if (anEnum.value.equals(value)) {
                    return anEnum;
                }
            }
            return null;
        }

        public Integer getValue() {
            return value;
        }

        public String getText() {
            return text;
        }
}
