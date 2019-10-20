package com.feng.plat.auth.service;

import com.feng.home.common.pagination.Page;
import com.feng.home.common.validate.AssertUtil;
import com.feng.home.plat.user.bean.RoleQueryCondition;
import com.feng.plat.auth.bean.Role;
import com.feng.plat.auth.dao.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class RoleService{

    @Autowired
    private RoleDao roleDao;
    public void saveRole(Role role) {
        Optional<Role> existRole = this.findRoleByCode(role.getCode());
        AssertUtil.assertFalse(existRole.isPresent(),"权限已存在");
        roleDao.saveBean(role);
    }

    public Optional<Role> findRoleByCode(String roleCode) {
        return roleDao.findRoleByCode(roleCode);
    }

    public List<Role> findRoleListByCodeList(Collection<String> roleCodeList) {
        return roleDao.getRoleListByCodeList(roleCodeList);
    }

    public Page<Role> pageQuery(RoleQueryCondition queryCondition, Page<Role> page) {
        return roleDao.pageQuery(queryCondition, page);
    }

    public void save(Role role) {
        this.roleDao.saveBean(role);
    }

    public void saveAll(List<Role> roleList) {
        this.roleDao.saveBeanList(roleList);
    }
}
