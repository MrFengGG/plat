package com.feng.plat.user.controller;

import com.feng.home.common.jdbc.pagination.Page;
import com.feng.home.common.resource.annotation.ResourceMeta;
import com.feng.home.common.validate.ValidationUtil;
import com.feng.home.plat.user.bean.SysUser;
import com.feng.home.plat.user.bean.condition.UserQueryCondition;
import com.feng.plat.user.service.SysUserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.sql.SQLException;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Resource
    private SysUserService sysUserService;

    @ResourceMeta(code = "USER_PAGE_QUERY", resourceName = "分页查询用户", url = "/user/query", group = "plat", enableAuthCheck = true)
    @RequestMapping(value = "/query")
    public Page<SysUser> query(UserQueryCondition userQueryCondition, @RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "10") int pageSize, String orderBy) throws SQLException {
        Page<SysUser> page = new Page<>();
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        page.setSortBy(orderBy);
        return sysUserService.pageQuery(userQueryCondition, page);
    }

    @ResourceMeta(code = "USER_ADD", resourceName = "新增用户", url = "/user/add", group = "plat")
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public void add(SysUser sysUser) throws SQLException {
        ValidationUtil.validate(sysUser);
        sysUserService.save(sysUser);
    }
}
