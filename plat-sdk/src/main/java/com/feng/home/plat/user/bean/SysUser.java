package com.feng.home.plat.user.bean;

import com.feng.home.common.bean.NoConvertField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUser {
    private int id;
    //用户名
    @NotNull(message = "用户名不能为空!")
    private String username;
    //密码
    @NotNull(message = "密码不能为空!")
    private String password;
    //名字
    private String realName;
    //昵称
    private String nickName;
    //电话
    private String mobile;
    //邮箱
    @Email
    private String email;
    //头像
    private String headImage;
    //生日
    private Date birthday;
    //上次登录时间
    private Date lastLoginTime;
    //上次登录ip
    private String lastLoginIp;
    //封禁开始时间
    private Date expireStartTime;
    //封禁结束时间
    private Date expireEndTime;
    //账号创建时间
    private Date createTime;
    //账号更新时间
    private Date updateTime;
    //微信openid
    private String wxOpenId;
    //账号状态
    private Integer status;
    //权限
    @NoConvertField
    private Collection<String> roles;
}
