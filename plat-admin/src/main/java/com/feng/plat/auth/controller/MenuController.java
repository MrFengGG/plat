package com.feng.plat.auth.controller;

import com.feng.plat.auth.base.TokenStore;
import com.feng.plat.auth.bean.Menu;
import com.feng.plat.auth.bean.Role;
import com.feng.plat.auth.service.MenuService;
import com.feng.plat.user.bean.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping(value = "/menu")
public class MenuController {
    @Resource
    private TokenStore<SysUser> tokenStore;

    @Autowired
    private MenuService menuService;

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public List<Menu> getMenus(HttpServletRequest request){
        String token = request.getHeader("token");
        Optional<SysUser> user = tokenStore.tokenToMessage(token);
        return user.map(user1 -> menuService.getMenuListByRoleList(user1.getRoles().stream().collect(toList()))).orElse(new LinkedList<>());
    }
}
