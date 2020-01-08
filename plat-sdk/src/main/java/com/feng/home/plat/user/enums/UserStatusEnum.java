package com.feng.home.plat.user.enums;

public enum UserStatusEnum {
    NORMAL(1,"正常",""),
    INVALID(0,"失效","");
    private int code;

    private String name;

    private String desc;

    UserStatusEnum(int code, String name, String desc) {
        this.code = code;
        this.name = name;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }
}
