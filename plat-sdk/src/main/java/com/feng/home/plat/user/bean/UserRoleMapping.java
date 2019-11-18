package com.feng.home.plat.user.bean;

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
public class UserRoleMapping {
    @NotNull(message = "角色代码不能为空!")
    private String roleCode;
    @NotNull(message = "用户id不能为空!")
    private int userId;
}
