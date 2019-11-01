package com.feng.plat.auth.bean;

import com.feng.home.common.bean.NoConvertField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Menu {
    private int id;
    @NotNull(message = "菜单代码不能为空!")
    private String code;
    @NotNull(message = "菜单类型不能为空!")
    private int menuType;
    @NotNull(message = "所属菜单组不能为空")
    private String menuGroupCode;

    private String icon;

    @NotNull(message = "菜单名称不能为空!")
    private String menuName;

    private String resource_code;

    private String menu_desc;

    private String parentCode;

    private Date createTime;

    private Date updateTime;

    @NotNull(message = "页面组件不能为空")
    private String componentName;

    private String componentParam;

    @NoConvertField
    private Resource resource;

    @NoConvertField
    private List<Role> needRoles;

    @NoConvertField
    private List<Menu> childList;
}
