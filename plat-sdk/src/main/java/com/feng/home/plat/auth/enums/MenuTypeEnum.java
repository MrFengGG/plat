package com.feng.home.plat.auth.enums;

public enum MenuTypeEnum {
    SUB(1, "父级菜单", "不可跳转"),
    COMPONENT(0, "可跳转菜单", "直接跳转");
    private int code;

    private String name;

    private String desc;

    MenuTypeEnum(int code, String name, String desc) {
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
