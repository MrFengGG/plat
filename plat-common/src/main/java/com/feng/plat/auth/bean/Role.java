package com.feng.plat.auth.bean;

import com.feng.home.common.support.ModelMapping;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ModelMapping(logicTable = "role")
public class Role {
    private int id;

    private String code;

    private String roleName;

    private String roleDesc;

    private Date createTime;

    private Date updateTime;
}
