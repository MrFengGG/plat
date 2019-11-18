package com.feng.plat.auth.service.impl;

import com.feng.home.common.common.StringUtil;
import com.feng.home.common.jdbc.pagination.Page;
import com.feng.home.common.validate.AssertUtil;
import com.feng.home.plat.auth.bean.Menu;
import com.feng.home.plat.auth.bean.MenuGroup;
import com.feng.home.plat.auth.bean.MenuRoleMapping;
import com.feng.home.plat.auth.bean.condition.MenuQueryCondition;
import com.feng.plat.auth.dao.MenuDao;
import com.feng.plat.auth.dao.MenuGroupDao;
import com.feng.plat.auth.dao.MenuRoleMappingDao;
import com.feng.plat.auth.dao.RoleDao;
import com.feng.plat.auth.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuRoleMappingDao MenuRoleMappingDao;
    @Autowired
    private MenuDao jdbcMenuDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private MenuGroupDao jdbcMenuGroupDao;

    /**
     * 根据角色获取菜单
     * @param roleCodeList
     * @param group
     * @return
     */
    @Override
    public List<Menu> getListByRoleList(String group, List<String> roleCodeList){
        List<MenuRoleMapping> menuRoleMappingList = MenuRoleMappingDao.getListByRoleCodeList(roleCodeList);
        List<Menu> menuList = jdbcMenuDao.getListByGroupCodeList(group, menuRoleMappingList.stream().map(MenuRoleMapping::getMenuCode).collect(toList()));
        return groupMenus(menuList);
    }

    /**
     * 根据角色获取菜单组
     * @param roleCodeList
     * @return
     */
    @Override
    public List<MenuGroup> getMenuGroupListByRoleList(List<String> roleCodeList){
        List<MenuRoleMapping> menuRoleMappingList = MenuRoleMappingDao.getListByRoleCodeList(roleCodeList);
        List<String> groupCodeList = jdbcMenuDao.getListByCodeList(menuRoleMappingList.stream().map(MenuRoleMapping::getMenuCode)
                .collect(toList())).stream().map(Menu::getMenuGroupCode).collect(toList());
        return jdbcMenuGroupDao.getListByCodeList(groupCodeList);
    }

    @Override
    public Page<Menu> pageQuery(List<String> roleCodeList, MenuQueryCondition condition, Page<Menu> page){
        List<MenuRoleMapping> menuRoleMappingList = MenuRoleMappingDao.getListByRoleCodeList(roleCodeList);
        List<String> menuCodeList = menuRoleMappingList.stream().map(MenuRoleMapping::getMenuCode).collect(toList());
        condition.setMenuCodeList(menuCodeList);
        return jdbcMenuDao.pageQuery(condition, page);
    }

    /**
     * 获取所有菜单
     * @return
     */
    @Override
    public List<Menu> getAll(){
        List<Menu> menuList = jdbcMenuDao.getAllMenuList();
        return groupMenus(menuList);
    }

    /**
     * 保存菜单
     * @param menu
     */
    @Override
    public void save(Menu menu){
        jdbcMenuDao.saveBean(menu);
    }

    /**
     * 给菜单授权
     * @param menuCode
     * @param roleCode
     */
    @Override
    public void giveMenuRole(String menuCode, String roleCode){
        AssertUtil.assertTrue(jdbcMenuDao.findByCode(menuCode).isPresent(), "菜单不存在");
        AssertUtil.assertTrue(roleDao.findByCode(roleCode).isPresent(), "角色不存在");
        AssertUtil.assertFalse(MenuRoleMappingDao.findFirst(roleCode, menuCode).isPresent(), "菜单已有该权限");
        MenuRoleMappingDao.saveBean(MenuRoleMapping.builder().menuCode(menuCode).roleCode(roleCode));
    }

    /**
     * 删除菜单
     * @param menuCode
     */
    @Override
    public void remove(String menuCode){
        jdbcMenuDao.removeByCode(menuCode);
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
