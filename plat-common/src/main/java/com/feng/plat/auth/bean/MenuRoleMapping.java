package com.feng.plat.auth.bean;

import com.feng.home.common.resource.ModelMapping;
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
@ModelMapping(logicTable = "menuRole")
public class MenuRoleMapping {
    private int id;
    @NotNull(message = "角色代码不能为空!")
    private String roleCode;
    @NotNull(message = "菜单代码不能为空!")
    private String menuCode;

    private Date createTime;
}
