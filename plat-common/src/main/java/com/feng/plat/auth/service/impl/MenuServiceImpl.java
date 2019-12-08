package com.feng.plat.auth.service.impl;

import com.feng.home.common.auth.AuthContext;
import com.feng.home.common.common.StringUtil;
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

import java.util.*;

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
        List<Menu> menuList = new LinkedList<>();
        if(AuthContext.currentUserInWhiteList()) {
            menuList = jdbcMenuDao.getEnableList();
        }else{
            List<MenuRoleMapping> menuRoleMappingList = MenuRoleMappingDao.getListByRoleCodeList(roleCodeList);
            jdbcMenuDao.getEnableListByGroupCodeList(group, menuRoleMappingList.stream().map(MenuRoleMapping::getMenuCode).collect(toList()));
        }
        return groupMenus(menuList);
    }

    /**
     * 根据角色获取菜单组
     * @param roleCodeList
     * @return
     */
    @Override
    public List<MenuGroup> getMenuGroupListByRoleList(List<String> roleCodeList){
        List<String> groupCodeList;
        if(AuthContext.currentUserInWhiteList()) {
            return jdbcMenuGroupDao.getAll();
        }else {
            List<MenuRoleMapping> menuRoleMappingList = MenuRoleMappingDao.getListByRoleCodeList(roleCodeList);
            groupCodeList =jdbcMenuDao.getEnableListByCodeList(menuRoleMappingList.stream().map(MenuRoleMapping::getMenuCode)
                    .collect(toList())).stream().map(Menu::getMenuGroupCode).collect(toList());
            return jdbcMenuGroupDao.getListByCodeList(groupCodeList);
        }
    }

    @Override
    public List<Menu> query(MenuQueryCondition condition) {
        return groupMenus(jdbcMenuDao.query(condition));
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

    @Override
    public void remove(Collection<String> menuCode){
        jdbcMenuDao.removeByCode(menuCode);
    }

    @Override
    public Optional<Menu> findById(Integer id) {
        return jdbcMenuDao.findById(id, Menu.class);
    }

    private List<Menu> groupMenus(List<Menu> menus){
        List<Menu> rootMenus = menus.stream().filter(menu -> StringUtil.isEmpty(menu.getParentCode())).collect(toList());
        return rootMenus.stream().map(rootMenu -> {
            setChild(rootMenu, menus);
            return rootMenu;
        }).collect(toList());
    }

    private void setChild(Menu root, List<Menu> menus){
        List<Menu> childMenu = menus.stream().filter(menu -> root.getCode().equals(menu.getParentCode())).collect(toList());
        if(childMenu.size() <= 0){
            return;
        }
        root.setChildList(childMenu.stream().map(menu -> {
            setChild(menu, menus);
            return menu;
        }).collect(toList()));
    }
}
