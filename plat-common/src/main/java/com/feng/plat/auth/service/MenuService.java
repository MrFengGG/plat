package com.feng.plat.auth.service;

import com.feng.home.common.jdbc.pagination.Page;
import com.feng.home.plat.auth.bean.Menu;
import com.feng.home.plat.auth.bean.MenuGroup;
import com.feng.home.plat.auth.bean.condition.MenuQueryCondition;

import java.sql.SQLException;
import java.util.List;

public interface MenuService {
    public List<Menu> getListByRoleList(String group, List<String> roleCodeList);

    public List<MenuGroup> getMenuGroupListByRoleList(List<String> roleCodeList);

    public Page<Menu> pageQuery(List<String> roleCodeList, MenuQueryCondition condition, Page<Menu> page);

    public List<Menu> getAll();

    public void save(Menu menu);

    public void giveMenuRole(String menuCode, String roleCode);

    public void remove(String menuCode);


}
