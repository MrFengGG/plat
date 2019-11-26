package com.feng.plat.auth.controller;

import com.feng.home.common.auth.AuthContext;
import com.feng.home.common.auth.bean.ContextUser;
import com.feng.home.common.jdbc.pagination.Page;
import com.feng.home.common.resource.annotation.ResourceMeta;
import com.feng.home.common.validate.ValidationUtil;
import com.feng.home.plat.auth.bean.Menu;
import com.feng.home.plat.auth.bean.MenuGroup;
import com.feng.plat.auth.service.MenuService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(value = "/menu")
public class MenuController {

    @Resource
    private MenuService menuService;

    @RequestMapping(value = "/getAllMenu", method = RequestMethod.GET)
    @ResourceMeta(code = "MENU_ALL", resourceName = "查询所有菜单", url = "/menu/getAllMenu", group = "plat")
    public List<Menu> getMenus(HttpServletRequest request){
        String group = request.getParameter("group");
        ContextUser contextUser = AuthContext.getContextUser();
        return menuService.getListByRoleList(group, new ArrayList<String>(contextUser.getRoleList()));
    }

    @RequestMapping(value = "/getAllGroup", method = RequestMethod.GET)
    @ResourceMeta(code = "MENU_GROUP_ALL", resourceName = "查询所有菜单组", url = "/menu/getAllGroup", group = "plat")
    public List<MenuGroup> getMenuGroups(HttpServletRequest request){
        ContextUser contextUser = AuthContext.getContextUser();
        return menuService.getMenuGroupListByRoleList(new ArrayList<>(contextUser.getRoleList()));
    }

    @RequestMapping(value = "/pageQueryMenu", method = RequestMethod.POST)
    @ResourceMeta(code = "MENU_PAGINATION", resourceName = "分页查询菜单", url = "/menu/pageQueryMenu", group = "plat")
    public Page<Menu> pageQuery(HttpServletRequest request){
        ContextUser contextUser = AuthContext.getContextUser();
        return menuService.pageQuery(new ArrayList<String>(contextUser.getRoleList()), null, null);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResourceMeta(code = "MENU_SAVE", resourceName = "保存菜单", url = "/menu/save", group = "plat")
    public void putMenu(@RequestBody Menu menu){
        ValidationUtil.validate(menu);
        menuService.save(menu);
    }

}
