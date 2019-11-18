package com.feng.home.plat.auth.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuRoleMapping {
    private int id;
    @NotNull(message = "角色代码不能为空!")
    private String roleCode;
    @NotNull(message = "菜单代码不能为空!")
    private String menuCode;

    private Date createTime;
}
