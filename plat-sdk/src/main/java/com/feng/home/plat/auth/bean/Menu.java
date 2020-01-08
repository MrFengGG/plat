package com.feng.home.plat.auth.bean;

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
    private Integer id;
    @NotNull(message = "菜单代码不能为空!")
    private String code;
    @NotNull(message = "菜单类型不能为空!")
    private int menuType;
    @NotNull(message = "所属菜单组不能为空")
    private String menuGroupCode;

    private String icon;

    @NotNull(message = "菜单名称不能为空!")
    private String menuName;

    private String menuDesc;

    private String parentCode;

    private Date createTime = new Date();

    private Date updateTime;

    private String menuPath;

    private Integer priority;

    //树路径
    private String treePath;

    //树深度
    private int treeDepth;

    @NoConvertField
    private List<Role> needRoles;

    @NoConvertField
    private List<Menu> childList;
}
