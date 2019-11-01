package com.feng.plat.auth.service.impl;

import com.feng.home.common.jdbc.pagination.Page;
import com.feng.home.common.validate.AssertUtil;
import com.feng.home.plat.user.bean.RoleQueryCondition;
import com.feng.plat.auth.bean.Role;
import com.feng.plat.auth.dao.RoleDao;
import com.feng.plat.auth.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;
    @Override
    public void saveRole(Role role) {
        Optional<Role> existRole = this.findByCode(role.getCode());
        AssertUtil.assertFalse(existRole.isPresent(),"权限已存在");
        roleDao.saveBean(role);
    }
    @Override
    public Optional<Role> findByCode(String roleCode) {
        return roleDao.findByCode(roleCode);
    }
    @Override
    public List<Role> getListByCodeList(Collection<String> roleCodeList) {
        return roleDao.getListByCodeList(roleCodeList);
    }
    @Override
    public Page<Role> pageQuery(RoleQueryCondition queryCondition, Page<Role> page) {
        return roleDao.pageQuery(queryCondition, page);
    }
    @Override
    public void saveAll(List<Role> roleList) {
        this.roleDao.saveBeanList(roleList);
    }
}
