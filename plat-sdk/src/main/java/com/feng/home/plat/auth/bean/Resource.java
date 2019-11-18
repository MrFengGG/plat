package com.feng.home.plat.auth.bean;

import com.feng.home.common.bean.NoConvertField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * create by FengZiyu
 * 2019/10/14
 * 资源表
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Resource {
    //资源id
    private int id;
    //资源代码
    @NotNull(message = "资源代码不能为空!")
    private String code;
    //资源名称
    private String resourceName;
    //资源描述
    private String resourceDesc;
    //资源链接
    private String url;
    //资源组
    private String resourceGroup;
    //父资源
    private String parentCode;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
    //可访问的角色
    @NoConvertField
    private List<Role> roleList;
}
