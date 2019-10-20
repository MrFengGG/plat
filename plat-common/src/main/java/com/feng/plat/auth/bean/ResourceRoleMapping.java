package com.feng.plat.auth.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResourceRoleMapping {
    private int id;

    private String roleCode;

    private String resourceId;
}
