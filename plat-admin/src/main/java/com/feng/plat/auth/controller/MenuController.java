package com.feng.plat.auth.controller;

import com.feng.plat.auth.base.TokenStore;
import com.feng.plat.auth.bean.Menu;
import com.feng.plat.auth.bean.MenuGroup;
import com.feng.plat.auth.service.MenuService;
import com.feng.plat.user.bean.SysUser;
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
    public List<Menu> getMenus(HttpServletRequest request){
        String token = request.getHeader("token");
        String group = request.getParameter("group");
        Optional<SysUser> userOptional = tokenStore.tokenToMessage(token);
        return userOptional.map(user -> menuService.getListByRoleList(group, new ArrayList<String>(user.getRoles()))).orElse(new LinkedList<>());
    }

    @RequestMapping(value = "/getAllGroup", method = RequestMethod.GET)
    public List<MenuGroup> getMenuGroups(HttpServletRequest request){
        String token = request.getHeader("token");
        Optional<SysUser> userOptional = tokenStore.tokenToMessage(token);
        return userOptional.map(user -> menuService.getMenuGroupListByRoleList(new ArrayList<String>(user.getRoles()))).orElse(new LinkedList<>());
    }

    @RequestMapping(value = "save", method = RequestMethod.PUT)
    public void putMenu(Menu menu){
        menuService.save(menu);
    }
}
