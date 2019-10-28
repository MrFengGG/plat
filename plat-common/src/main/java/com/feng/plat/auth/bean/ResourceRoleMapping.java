package com.feng.plat.auth.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResourceRoleMapping {
    private int id;
    @NotNull(message = "角色代码不能为空!")
    private String roleCode;
    @NotNull(message = "资源id不能为空!")
    private String resourceId;
}
