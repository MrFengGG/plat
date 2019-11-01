package com.feng.plat.auth.service;

import com.feng.plat.auth.bean.Menu;
import com.feng.plat.auth.bean.MenuGroup;

import java.util.List;

public interface MenuService {
    public List<Menu> getListByRoleList(String group, List<String> roleCodeList);

    public List<MenuGroup> getMenuGroupListByRoleList(List<String> roleCodeList);

    public List<Menu> getAll();

    public void save(Menu menu);

    public void giveMenuRole(String menuCode, String roleCode);

    public void remove(String menuCode);


}
