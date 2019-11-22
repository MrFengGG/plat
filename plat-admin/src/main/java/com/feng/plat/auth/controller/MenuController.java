package com.feng.plat.auth.controller;

import com.feng.home.common.jdbc.pagination.Page;
import com.feng.home.common.resource.annotation.ResourceMeta;
import com.feng.plat.auth.base.TokenStore;
import com.feng.home.plat.auth.bean.Menu;
import com.feng.home.plat.auth.bean.MenuGroup;
import com.feng.plat.auth.service.MenuService;
import com.feng.home.plat.user.bean.SysUser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(value = "/menu")
public class MenuController {
    @Resource
    private TokenStore<SysUser> tokenStore;

    @Resource
    private MenuService menuService;

    @RequestMapping(value = "/getAllMenu", method = RequestMethod.GET)
    @ResourceMeta(code = "MENU_ALL", resourceName = "查询所有菜单", url = "/menu/getAllMenu", group = "plat")
    public List<Menu> getMenus(HttpServletRequest request){
        String token = request.getHeader("token");
        String group = request.getParameter("group");
        Optional<SysUser> userOptional = tokenStore.tokenToMessage(token);
        return userOptional.map(user -> menuService.getListByRoleList(group, new ArrayList<String>(user.getRoles()))).orElse(new LinkedList<>());
    }

    @RequestMapping(value = "/getAllGroup", method = RequestMethod.GET)
    @ResourceMeta(code = "MENU_GROUP_ALL", resourceName = "查询所有菜单组", url = "/menu/getAllGroup", group = "plat")
    public List<MenuGroup> getMenuGroups(HttpServletRequest request){
        String token = request.getHeader("token");
        Optional<SysUser> userOptional = tokenStore.tokenToMessage(token);
        return userOptional.map(user -> menuService.getMenuGroupListByRoleList(new ArrayList<String>(user.getRoles()))).orElse(new LinkedList<>());
    }

    @RequestMapping(value = "/pageQueryMenu", method = RequestMethod.POST)
    @ResourceMeta(code = "MENU_PAGINATION", resourceName = "分页查询菜单", url = "/menu/pageQueryMenu", group = "plat", enableAuthCheck = true)
    public Page<Menu> pageQuery(HttpServletRequest request){
        String token = request.getHeader("token");
        Optional<SysUser> userOptional = tokenStore.tokenToMessage(token);
        return userOptional.map(user -> menuService.pageQuery(new ArrayList<String>(user.getRoles()), null, null)).orElse(new Page<>());
    }

    @RequestMapping(value = "/save", method = RequestMethod.PUT)
    @ResourceMeta(code = "MENU_SAVE", resourceName = "保存菜单", url = "/menu/save", group = "plat", enableAuthCheck = true)
    public void putMenu(Menu menu){
        menuService.save(menu);
    }

}
