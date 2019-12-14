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

    public void giveMenuRole(String menuCode, String roleCode);

    public void remove(Collection<String> code);

    public Optional<Menu> findById(Integer id);

    public boolean updateMenu(Menu menu);
}
