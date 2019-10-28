package com.feng.plat.user.bean;

import com.feng.home.common.resource.ModelMapping;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 会员->角色映射
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ModelMapping(logicTable = "user_role")
public class UserRoleMapping {
    @NotNull(message = "角色代码不能为空!")
    private String roleCode;
    @NotNull(message = "用户id不能为空!")
    private int userId;
}
