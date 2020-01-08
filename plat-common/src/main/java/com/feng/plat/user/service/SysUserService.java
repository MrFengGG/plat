package com.feng.plat.user.service;

import com.feng.home.common.jdbc.pagination.Page;
import com.feng.home.plat.user.bean.condition.UserQueryCondition;
import com.feng.home.plat.user.bean.SysUser;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface SysUserService {
    public Optional<SysUser> findByUsername(String username);

    public SysUser login(String username, String password);

    public SysUser save(SysUser user);

    public Page<SysUser> pageQuery(UserQueryCondition userQueryCondition, Page<SysUser> page) throws SQLException;

    public void update(SysUser sysUser);

    public void giveRole(Integer userId, Collection<String> roleList);

    public void freeze(Integer userId, Date startTime, Date endTime);

    public void invalid(Integer userId);
}
