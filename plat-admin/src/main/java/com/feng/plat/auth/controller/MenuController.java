package com.feng.plat.auth.controller;

import com.feng.home.common.auth.AuthContext;
import com.feng.home.common.auth.bean.ContextUser;
import com.feng.home.common.jdbc.pagination.Page;
import com.feng.home.common.resource.annotation.ResourceMeta;
import com.feng.home.common.utils.RequestUtils;
import com.feng.home.common.validate.ValidationUtil;
import com.feng.home.plat.auth.bean.Menu;
import com.feng.home.plat.auth.bean.MenuGroup;
import com.feng.home.plat.auth.bean.condition.MenuQueryCondition;
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

    @RequestMapping(value = "/get_user_menu", method = RequestMethod.GET)
    @ResourceMeta(code = "MENU-GET_USER_MENU", resourceName = "查询用户可访问菜单", url = "/menu/get_user_menu", group = "plat")
    public List<Menu> getUserMenus(HttpServletRequest request){
        String group = request.getParameter("group");
        ContextUser contextUser = AuthContext.getContextUser();
        return menuService.getListByRoleList(group, new ArrayList<>(contextUser.getRoleList()));
    }

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    @ResourceMeta(code = "MENU-QUERY", resourceName = "查询所有菜单", url = "/menu/query", group = "plat")
    public List<Menu> query(MenuQueryCondition menuQueryCondition){
        return menuService.query(menuQueryCondition);
    }

    @RequestMapping(value = "/find_by_id", method = RequestMethod.GET)
    @ResourceMeta(code = "MENU-FIND_BY_ID", resourceName = "查询菜单详情", url = "/menu/find_by_id", group = "plat")
    public Menu findById(Integer id){
        return menuService.findById(id).orElse(null);
    }

    @RequestMapping(value = "/get_menu_groups", method = RequestMethod.GET)
    @ResourceMeta(code = "MENU-GET_MENU_GROUPS", resourceName = "查询所有菜单组", url = "/menu/get_menu_groups", group = "plat")
    public List<MenuGroup> getMenuGroups(){
        ContextUser contextUser = AuthContext.getContextUser();
        return menuService.getMenuGroupListByRoleList(new ArrayList<>(contextUser.getRoleList()));
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResourceMeta(code = "MENU-SAVE", resourceName = "保存菜单", url = "/menu/save", group = "plat")
    public void save(@RequestBody Menu menu){
        ValidationUtil.validate(menu);
        menuService.save(menu);
    }

}
