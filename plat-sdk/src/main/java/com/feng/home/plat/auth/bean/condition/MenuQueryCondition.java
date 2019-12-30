package com.feng.home.plat.auth.bean.condition;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Date;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuQueryCondition {
    private String menuName;
    private String menuGroupCode;
    private Date[] createTimeTuple;
    private String menuPath;
    private Collection<String> menuCodeList;
    private Integer enable;
    private String groupCode;
    private String orderBy = "priority";
    private String parentCode;
}
