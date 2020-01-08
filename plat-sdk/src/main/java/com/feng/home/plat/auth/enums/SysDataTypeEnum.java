package com.feng.home.plat.auth.enums;

/**
 * create by FengZiyu
 * 2020/01/08
 */
public enum SysDataTypeEnum {
    SUPER_ADMIN("超级管理员", 0),
    ADMIN("管理员", 1),
    NORMAL("普通", 2);
    private Integer code;

    private String name;

    SysDataTypeEnum(String name, Integer code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public Integer getCode() {
        return code;
    }
}
