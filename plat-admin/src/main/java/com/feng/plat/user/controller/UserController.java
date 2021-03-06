package com.feng.plat.user.controller;

import com.feng.home.common.collection.Dict;
import com.feng.home.common.exception.SampleBusinessException;
import com.feng.home.common.jdbc.pagination.Page;
import com.feng.home.common.resource.annotation.ResourceMeta;
import com.feng.home.common.validate.AssertUtil;
import com.feng.home.common.validate.ValidationUtil;
import com.feng.home.plat.user.bean.SysUser;
import com.feng.home.plat.user.bean.condition.UserQueryCondition;
import com.feng.plat.user.service.SysUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Resource
    private SysUserService sysUserService;

    @ResourceMeta(code = "USER-PAGINATION_QUERY", resourceName = "分页查询用户", url = "/user/pagination_query", group = "plat")
    @RequestMapping(value = "/pagination_query", method = RequestMethod.GET)
    public Page<SysUser> paginationQuery(UserQueryCondition userQueryCondition, @RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "10") int pageSize, String orderBy) throws SQLException {
        Page<SysUser> page = new Page<>();
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        page.setSortBy(orderBy);
        return sysUserService.pageQuery(userQueryCondition, page);
    }

    @ResourceMeta(code = "USER-GET", resourceName = "单个用户查询", url = "/user/get", group = "plat")
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public SysUser get(Integer userId){
        AssertUtil.assertNotNull(userId, "用户ID不能为空");
        return sysUserService.findById(userId).orElse(null);
    }

    @ResourceMeta(code = "USER-ADD", resourceName = "新增用户", url = "/user/save", group = "plat")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public SysUser save(@RequestBody SysUser sysUser){
        ValidationUtil.validate(sysUser);
        sysUser.setPassword("000000");
        return sysUserService.save(sysUser);
    }

    @ResourceMeta(code = "USER-UPDATE", resourceName = "修改用户", url = "/user/update", group = "plat")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public void update(@RequestBody SysUser sysUser){
        ValidationUtil.validate(sysUser);
        sysUserService.update(sysUser);
    }

    @ResourceMeta(code = "USER-GIVE_ROLE", resourceName = "用户赋角色", url = "/user/give_role", group = "plat")
    @RequestMapping(value = "/give_role", method = RequestMethod.POST)
    public void giveRole(@RequestBody Dict dict){
        Collection<String> roleList = dict.getCollection("roleList");
        Integer userId = dict.getInt("userId");
        sysUserService.giveRole(userId, roleList);
    }

    @ResourceMeta(code = "USER-FREEZE", resourceName = "冻结用户", url = "/user/freeze", group = "plat")
    @RequestMapping(value = "/freeze", method = RequestMethod.POST)
    public void freeze(@RequestBody Dict dict){
        Date endTime = dict.getOrDefault("endTime", Date.class, new Date());
        Integer userId = dict.getInt("userId");
        sysUserService.freeze(userId, new Date(), endTime);
    }
}
