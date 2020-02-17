package com.feng.plat.auth.controller;

import com.feng.home.common.collection.Dict;
import com.feng.home.common.jdbc.pagination.Page;
import com.feng.home.common.resource.annotation.ResourceMeta;
import com.feng.home.common.utils.RequestUtils;
import com.feng.home.common.validate.AssertUtil;
import com.feng.home.common.validate.ValidationUtil;
import com.feng.home.plat.auth.bean.Role;
import com.feng.home.plat.auth.bean.condition.RoleQueryCondition;
import com.feng.home.plat.auth.enums.SysDataTypeEnum;
import com.feng.plat.auth.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(value = "/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/pagination_query", method = RequestMethod.GET)
    @ResourceMeta(code = "ROLE-PAGINATION_QUERY", resourceName = "分页查询角色", url = "/role/pagination_query", group = "plat")
    public Page<Role> paginationQuery(RoleQueryCondition roleQueryCondition, HttpServletRequest request){
        Page<Role> page = RequestUtils.getPage(request);
        return roleService.pageQuery(roleQueryCondition, page);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResourceMeta(code = "ROLE-SAVE", resourceName = "保存角色", url = "/role/save", group = "plat")
    public void save(@RequestBody Role role){
        //临时处理
        role.setRoleType(SysDataTypeEnum.NORMAL.getCode());
        ValidationUtil.validate(role);
        this.roleService.saveRole(role);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResourceMeta(code = "ROLE-UPDATE", resourceName = "修改角色", url = "/role/update", group = "plat")
    public void update(@RequestBody Role role){
        ValidationUtil.validate(role);
        this.roleService.updateRole(role);
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ResourceMeta(code = "ROLE-REMOVE", resourceName = "删除角色", url = "/role/remove", group = "plat")
    public void remove(@RequestBody Dict dict){
        String roleCode = dict.getStr("roleCode");
        AssertUtil.assertNotNull(roleCode, "角色代码不能为空");
        roleService.remove(roleCode);
    }

    @RequestMapping(value = "/get_all", method = RequestMethod.GET)
    @ResourceMeta(code = "ROLE-GET_ALL", resourceName = "查询所有角色", url = "/role/get_all", group = "plat")
    public List<Role> allRoleList(){
        return roleService.getAllRole();
    }

    @RequestMapping(value = "/get_menu_role_list", method = RequestMethod.GET)
    @ResourceMeta(code = "ROLE-GET_MENU_ROLE_LIST", resourceName = "查询菜单所有角色", url = "/role/get_menu_role_list", group = "plat")
    public List<Role> getMenuRoleList(@NotNull String menuCode){
        return roleService.getByMenuCode(menuCode);
    }

    @RequestMapping(value = "/get_user_role_list", method = RequestMethod.GET)
    @ResourceMeta(code = "ROLE-GET_USER_ROLE_LIST", resourceName = "查询用户所有角色", url = "/role/get_user_role_list", group = "plat")
    public List<Role> getUserRoleList(@NotNull Integer userId){
        return roleService.getByUserId(userId);
    }
}