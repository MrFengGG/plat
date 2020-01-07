package com.feng.plat.auth.service.impl;

import com.feng.home.common.auth.AuthContext;
import com.feng.home.common.common.StringUtil;
import com.feng.home.common.exception.SampleBusinessException;
import com.feng.home.plat.auth.bean.Menu;
import com.feng.home.plat.auth.bean.MenuGroup;
import com.feng.home.plat.auth.bean.MenuRoleMapping;
import com.feng.home.plat.auth.bean.Role;
import com.feng.home.plat.auth.bean.condition.MenuQueryCondition;
import com.feng.plat.auth.dao.MenuDao;
import com.feng.plat.auth.dao.MenuGroupDao;
import com.feng.plat.auth.dao.MenuRoleMappingDao;
import com.feng.plat.auth.dao.RoleDao;
import com.feng.plat.auth.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

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

    @Override
    public List<Menu> getListByRoleList(String group, List<String> roleCodeList){
        List<Menu> menuList = new LinkedList<>();
        if(AuthContext.currentUserInWhiteList()) {
            menuList = jdbcMenuDao.getEnableList();
        }else{
            List<MenuRoleMapping> menuRoleMappingList = MenuRoleMappingDao.getListByRoleCodeList(roleCodeList);
            menuList = jdbcMenuDao.getEnableListByGroupCodeList(group, menuRoleMappingList.stream().map(MenuRoleMapping::getMenuCode).collect(toList()));
        }
        return groupMenus(menuList);
    }

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
        List<Menu> menuList = jdbcMenuDao.query(condition);
        return groupMenus(fillMenuWithRole(menuList));
    }

    @Override
    public void save(Menu menu){
        jdbcMenuDao.saveBean(menu);
    }

    @Override
    public void giveMenuRoles(String menuCode, Collection<String> roleCodeList){
        MenuRoleMappingDao.removeBy("menu_code", menuCode);
        List<MenuRoleMapping> roleMappingList = roleCodeList.stream().map(role -> MenuRoleMapping.builder().createTime(new Date()).menuCode(menuCode).roleCode(role).build()).collect(toList());
        MenuRoleMappingDao.saveBeanList(roleMappingList);
    }

    @Override
    public void remove(String menuCode, Boolean recursion){
        if(StringUtil.isEmpty(menuCode)){
            throw new SampleBusinessException("菜单代码不能为空");
        }
        Optional<MenuRoleMapping> menuRoleMappingOptional = this.MenuRoleMappingDao.findBy("code", menuCode, MenuRoleMapping.class);
        if(menuRoleMappingOptional.isPresent()){
            throw new SampleBusinessException("有与该菜单绑定的角色关系!");
        }
        MenuQueryCondition menuQueryCondition = MenuQueryCondition.builder().parentCode(menuCode).build();
        List<Menu> childrenMenuList = jdbcMenuDao.query(menuQueryCondition);
        if(childrenMenuList.size() > 0 && !recursion){
            throw new SampleBusinessException("该菜单包含子节点!");
        }
        List<String> deleteMenuCode = Stream.of(menuCode).collect(toList());
        deleteMenuCode.addAll(childrenMenuList.stream().map(Menu::getCode).collect(toList()));
        this.jdbcMenuDao.removeByCode(deleteMenuCode);
    }

    @Override
    public Optional<Menu> findById(Integer id) {
        return jdbcMenuDao.findById(id, Menu.class);
    }

    @Override
    public boolean updateMenu(Menu menu) {
        Optional<Menu> targetMenuOptional = this.findById(menu.getId());
        if(!targetMenuOptional.isPresent()){
            throw new SampleBusinessException("要修改的菜单不存在!");
        }
        if(menu.getCode().equals(targetMenuOptional.get().getParentCode())){
            throw new SampleBusinessException("不允许修改菜单代码!");
        }
        menu.setUpdateTime(new Date());
        return jdbcMenuDao.updateById(menu) > 0;
    }

    private List<Menu> fillMenuWithRole(List<Menu> menuList){
        List<String> menuCodeList = menuList.stream().map(Menu::getCode).collect(toList());
        List<MenuRoleMapping> menuRoleMappingList = MenuRoleMappingDao.getListByMenuCodeList(menuCodeList);
        Map<String, List<MenuRoleMapping>> menuRoleCodeMapping = menuRoleMappingList.stream().collect(groupingBy(MenuRoleMapping::getMenuCode));
        List<String> roleCodeList = menuRoleMappingList.stream().map(MenuRoleMapping::getRoleCode).collect(toList());
        List<Role> roleList = roleDao.getListByCodeList(roleCodeList);
        Map<String, Role> roleMapping = roleList.stream().collect(toMap(Role::getCode, Function.identity()));
        return menuList.stream().peek(menu -> {
            List<MenuRoleMapping> menuRoleMappings = menuRoleCodeMapping.getOrDefault(menu.getCode(), new LinkedList<>());
            List<Role> menuRoleList = menuRoleMappings.stream().map(MenuRoleMapping::getRoleCode).map(roleMapping::get).collect(toList());
            menu.setNeedRoles(menuRoleList);
        }).collect(toList());
    }

    private List<Menu> groupMenus(List<Menu> menus){
        List<Menu> rootMenus = menus.stream().filter(menu -> StringUtil.isEmpty(menu.getParentCode())).collect(toList());
        return rootMenus.stream().peek(rootMenu -> setChild(rootMenu, menus)).collect(toList());
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
