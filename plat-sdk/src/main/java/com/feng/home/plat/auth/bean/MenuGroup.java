package com.feng.home.plat.auth.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuGroup {
    private Integer id;

    private String groupName;

    private String code;

    private String groupDesc;

    private String indexPath;

    private Integer priority;
}
