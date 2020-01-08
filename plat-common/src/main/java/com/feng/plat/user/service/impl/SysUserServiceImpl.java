package com.feng.plat.user.service.impl;

import com.feng.home.common.common.PasswordUtil;
import com.feng.home.common.exception.SampleBusinessException;
import com.feng.home.common.jdbc.pagination.Page;
import com.feng.home.common.validate.AssertUtil;
import com.feng.home.plat.user.enums.UserStatusEnum;
import com.feng.home.plat.user.bean.SysUser;
import com.feng.home.plat.user.bean.condition.UserQueryCondition;
import com.feng.home.plat.user.bean.UserRoleMapping;
import com.feng.plat.user.dao.SysUserDao;
import com.feng.plat.user.dao.UserRoleMappingDao;
import com.feng.plat.user.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserDao sysUserDao;

    @Autowired
    private UserRoleMappingDao userRoleMappingDao;


    public Optional<SysUser> findByUsername(String username) {
        Optional<SysUser> sysUser = sysUserDao.findUserByUsername(username);
        return sysUser.map(user -> {
            //连接用户角色
            List<UserRoleMapping> userRoleMappings = userRoleMappingDao.getUserRoleMappingListByUserId(user.getId());
            user.setRoles(userRoleMappings.stream().map(UserRoleMapping::getRoleCode).collect(toList()));
            return user;
        });
    }

    public SysUser login(String username, String password) {
        Optional<SysUser> sysUser = findByUsername(username);
        if(!sysUser.isPresent() || !PasswordUtil.match(password, sysUser.get().getPassword())){
            throw new SampleBusinessException("账户不存在或密码错误");
        }
        Optional<Date> expireEndTime = sysUser.map(SysUser::getExpireEndTime);
        AssertUtil.assertFalse(expireEndTime.map(new Date()::before).orElse(false), "该账号在封禁中");
        return sysUser.map(user -> {
            user.setPassword("*");
            return user;
        }).get();
    }

    public SysUser save(SysUser user) {
        user.setPassword(PasswordUtil.encode(user.getPassword()));
        user.setStatus(UserStatusEnum.NORMAL.getCode());
        sysUserDao.saveBean(user);
        return sysUserDao.findUserByUsername(user.getUsername()).orElse(null);
    }

    public Page<SysUser> pageQuery(UserQueryCondition userQueryCondition, Page<SysUser> page) throws SQLException {
        Page<SysUser> pageData = sysUserDao.pageQuery(userQueryCondition, page);

        List<Integer> userIdSet = pageData.getData().stream().map(SysUser::getId).collect(Collectors.toList());
        List<UserRoleMapping> userRoleMappingList = userRoleMappingDao.getUserRoleMappingListByUserIdList(userIdSet);

        Map<Integer, List<UserRoleMapping>> mappingMap = userRoleMappingList.stream().collect(groupingBy(UserRoleMapping::getUserId));
        List<SysUser> perfectUserList = pageData.getData().stream().map(sysUser -> {
            Collection<String> userRoles = mappingMap.get(sysUser.getId()).stream().map(UserRoleMapping::getRoleCode).collect(toList());
            sysUser.setRoles(userRoles);
            sysUser.setPassword("*");
            return sysUser;
        }).collect(toList());
        page.setData(perfectUserList);
        return page;
    }

    @Override
    public void update(SysUser sysUser) {
        this.sysUserDao.updateById(sysUser);
    }

    @Override
    public void giveRole(Integer userId, Collection<String> roleList) {
        userRoleMappingDao.removeBy("user_id", userId);
        List<UserRoleMapping> userRoleMappingList = roleList.stream().map(roleCode -> UserRoleMapping.builder().createTime(new Date()).roleCode(roleCode).userId(userId).build()).collect(toList());
        userRoleMappingDao.saveBeanList(userRoleMappingList);
    }

    @Override
    public void freeze(Integer userId, Date startTime, Date endTime) {
        SysUser sysUser =
    }

    @Override
    public void invalid(Integer userId) {

    }
}
