package com.feng.plat.auth.service;

import com.feng.home.common.common.StringUtil;
import com.feng.home.common.validate.AssertUtil;
import com.feng.plat.auth.bean.Menu;
import com.feng.plat.auth.bean.MenuRoleMapping;
import com.feng.plat.auth.dao.MenuDao;
import com.feng.plat.auth.dao.MenuRoleMappingDao;
import com.feng.plat.auth.dao.RoleDao;
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
    @Autowired
    private RoleDao roleDao;

    /**
     * 根据角色获取菜单
     * @param roleCodeList
     * @return
     */
    public List<Menu> getMenuListByRoleList(List<String> roleCodeList){
        List<MenuRoleMapping> menuRoleMappingList = menuRoleMappingDao.getListByRoleCodeList(roleCodeList);
        List<Menu> menuList = menuDao.getMenuListByCodeList(menuRoleMappingList.stream().map(MenuRoleMapping::getMenuCode).collect(toList()));
        return groupMenus(menuList);
    }

    /**
     * 获取所有菜单
     * @return
     */
    public List<Menu> getAllMenuList(){
        List<Menu> menuList = menuDao.getAllMenuList();
        return groupMenus(menuList);
    }

    /**
     * 保存菜单
     * @param menu
     */
    public void saveMenu(Menu menu){
        menuDao.saveBean(menu);
    }

    /**
     * 给菜单授权
     * @param menuCode
     * @param roleCode
     */
    public void giveMenuRole(String menuCode, String roleCode){
        AssertUtil.assertTrue(menuDao.findMenuByCode(menuCode).isPresent(), "菜单不存在");
        AssertUtil.assertTrue(roleDao.findRoleByCode(roleCode).isPresent(), "角色不存在");
        AssertUtil.assertFalse(menuRoleMappingDao.findFirst(roleCode, menuCode).isPresent(), "菜单已有该权限");
        menuRoleMappingDao.saveBean(MenuRoleMapping.builder().menuCode(menuCode).roleCode(roleCode));
    }

    /**
     * 删除菜单
     * @param menuCode
     */
    public void removeMenu(String menuCode){
        menuDao.removeByCode(menuCode);
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
