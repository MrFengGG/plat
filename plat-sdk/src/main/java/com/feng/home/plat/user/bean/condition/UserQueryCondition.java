package com.feng.home.plat.user.bean.condition;

import com.feng.home.plat.user.enums.UserStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserQueryCondition {
    private Date registerStartTime;

    private Date registerEndTime;

    private String username;

    private String email;

    private String realName;

    private String mobile;

    private List<UserStatusEnum> statusList;
}
