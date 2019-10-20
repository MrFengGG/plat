package com.feng.home.plat.user.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleQueryCondition {
    private String roleName;

    private String roleDesc;

    private Date createStartTime;

    private Date createEndTime;

    private Date updateStartTime;

    private Date updateEndTime;
}
