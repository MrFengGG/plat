package com.feng.plat.auth.bean;

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
public class Role {
    private int id;
    @NotNull(message = "角色代码不能为空!")
    private String code;
    @NotNull(message = "角色名称不能为空!")
    private String roleName;

    private String roleDesc;

    private Date createTime;

    private Date updateTime;
}
