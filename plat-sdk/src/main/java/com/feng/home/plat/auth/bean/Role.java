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
public class Role {
    private Integer id;
    @NotNull(message = "角色代码不能为空!")
    private String code;
    @NotNull(message = "角色名称不能为空!")
    private String roleName;
    //角色描述
    private String roleDesc;
    //父角色
    private String parentCode;
    //树路径
    private String treePath;
    //树深度
    private int treeDepth;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
    //角色类型0:超级管理员角色,1:管理员角色,2:普通用户角色
    private int roleType;
}
