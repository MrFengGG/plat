package com.feng.plat.auth.service;

import com.feng.home.plat.auth.bean.Menu;
import com.feng.home.plat.auth.bean.MenuGroup;
import com.feng.home.plat.auth.bean.condition.MenuQueryCondition;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MenuService {
    public List<Menu> getListByRoleList(String group, List<String> roleCodeList);

    public List<MenuGroup> getMenuGroupListByRoleList(List<String> roleCodeList);

    public List<Menu> query(MenuQueryCondition condition);

    public void save(Menu menu);

    public void giveMenuRoles(String menuCode, Collection<String> roleCodeList);

    public void remove(String code);

    public Optional<Menu> findById(Integer id);

    public boolean updateMenu(Menu menu);

    public List<String> getMenuRole(String menuCode);
}
