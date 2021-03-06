package com.feng.plat.auth.service;

import com.feng.home.common.jdbc.pagination.Page;
import com.feng.home.plat.auth.bean.condition.RoleQueryCondition;
import com.feng.home.plat.auth.bean.Role;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface RoleService {
    public void saveRole(Role role);

    public Optional<Role> findByCode(String roleCode);

    public List<Role> getAllRole();

    public List<Role> getListByCodeList(Collection<String> roleCodeList);

    public Page<Role> pageQuery(RoleQueryCondition queryCondition, Page<Role> page);

    public void updateRole(Role role);

    public void remove(String roleCode);

    public List<Role> getByMenuCode(String menuCode);

    public List<Role> getByUserId(Integer userId);
}
