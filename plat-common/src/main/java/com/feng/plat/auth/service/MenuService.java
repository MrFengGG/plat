package com.feng.plat.auth.service;

import com.feng.home.common.common.StringUtil;
import com.feng.plat.auth.bean.Menu;
import com.feng.plat.auth.bean.MenuRoleMapping;
import com.feng.plat.auth.dao.MenuDao;
import com.feng.plat.auth.dao.MenuRoleMappingDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class MenuService {
    @Autowired
    private MenuRoleMappingDao menuRoleMappingDao;
    @Autowired
    private MenuDao menuDao;

    public List<Menu> getMenuListByRoleList(List<String> roleCodeList){
        List<MenuRoleMapping> menuRoleMappingList = menuRoleMappingDao.getListByRoleCodeList(roleCodeList);
        List<Menu> menuList = menuDao.getMenuListByCodeList(menuRoleMappingList.stream().map(MenuRoleMapping::getMenuCode).collect(toList()));
        return groupMenus(menuList);
    }

    private List<Menu> groupMenus(List<Menu> menus){
        List<Menu> rootMenus = menus.stream().filter(menu -> StringUtil.isEmpty(menu.getParentCode())).collect(toList());
        return rootMenus.stream().map(rootMenu -> {
            setChild(rootMenu, menus);
            return rootMenu;
        }).collect(toList());
    }

    private void setChild(Menu root, List<Menu> menus){
        List<Menu> childMenu = menus.stream().filter(menu -> menu.getParentCode().equals(root.getCode())).collect(toList());
        if(childMenu.size() <= 0){
            return;
        }
        root.setChildList(childMenu.stream().map(menu -> {
            setChild(menu, menus);
            return menu;
        }).collect(toList()));
    }
}
