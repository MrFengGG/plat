package com.feng.plat.auth.bean;

import com.feng.home.common.support.ModelMapping;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ModelMapping(logicTable = "menuRole")
public class MenuRoleMapping {
    private int id;

    private String roleCode;

    private String menuCode;
}
