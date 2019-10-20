package com.feng.plat.user.bean;

import com.feng.home.common.support.ModelMapping;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 会员->角色映射
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ModelMapping(logicTable = "user_role")
public class UserRoleMapping {
    private String roleCode;

    private int userId;
}
