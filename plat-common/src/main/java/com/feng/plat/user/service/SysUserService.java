package com.feng.plat.user.service;

import com.feng.home.common.jdbc.pagination.Page;
import com.feng.home.plat.user.bean.condition.UserQueryCondition;
import com.feng.home.plat.user.bean.SysUser;

import java.sql.SQLException;
import java.util.Optional;

public interface SysUserService {
    public Optional<SysUser> findByUsername(String username);

    public SysUser login(String username, String password);

    public void save(SysUser user);

    public Page<SysUser> pageQuery(UserQueryCondition userQueryCondition, Page<SysUser> page) throws SQLException;

}
