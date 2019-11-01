package com.feng.plat.auth.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuGroup {
    private int id;

    private String groupName;

    private String code;

    private String groupDesc;
}
