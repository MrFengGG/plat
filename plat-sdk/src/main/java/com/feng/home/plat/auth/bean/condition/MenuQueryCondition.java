package com.feng.home.plat.auth.bean.condition;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MenuQueryCondition {
    private String name;

    private List<String> menuCodeList;
}
