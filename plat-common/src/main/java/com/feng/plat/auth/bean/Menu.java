package com.feng.plat.auth.bean;

import com.feng.home.common.support.ModelMapping;
import com.feng.home.common.support.NoDbField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ModelMapping(logicTable = "menu")
public class Menu {
    private int id;

    private String code;

    private int type;

    private String icon;

    private String menuName;

    private String resource_code;

    private String menu_desc;

    private String parentCode;

    private Date createTime;

    private Date updateTime;

    @NoDbField
    private Resource resource;

    @NoDbField
    private List<Role> needRoles;

    @NoDbField
    private List<Menu> childList;
}
