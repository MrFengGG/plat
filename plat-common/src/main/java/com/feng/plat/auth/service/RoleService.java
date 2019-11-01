package com.feng.plat.auth.service;

import com.feng.home.common.jdbc.pagination.Page;
import com.feng.home.plat.user.bean.RoleQueryCondition;
import com.feng.plat.auth.bean.Role;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface RoleService {
    public void saveRole(Role role);

    public Optional<Role> findByCode(String roleCode);

    public List<Role> getListByCodeList(Collection<String> roleCodeList);

    public Page<Role> pageQuery(RoleQueryCondition queryCondition, Page<Role> page);

    public void saveAll(List<Role> roleList);
}
