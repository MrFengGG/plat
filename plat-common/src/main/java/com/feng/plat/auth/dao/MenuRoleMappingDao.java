package com.feng.plat.auth.dao;

import com.feng.home.common.jdbc.base.BaseMappingDao;
import com.feng.home.common.jdbc.base.DaoMapping;
import com.feng.home.common.sql.SqlBuilder;
import com.feng.home.plat.auth.bean.MenuRoleMapping;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
@DaoMapping(logicTable = "menu_role")
public class MenuRoleMappingDao extends BaseMappingDao{

    @Override
    @Resource
    protected void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<MenuRoleMapping> getListByRoleCodeList(List<String> roleList){
        SqlBuilder sqlBuilder = SqlBuilder.init("select * from").joinDirect(this.getTable()).joinIn("where role_code", roleList);
        return this.queryForAllBean(MenuRoleMapping.class, sqlBuilder);
    }

    public List<MenuRoleMapping> getListByMenuCodeList(Collection<String> menuCodeList){
        SqlBuilder sqlBuilder = SqlBuilder.init("select * from").joinDirect(this.getTable()).joinIn("where role_code", menuCodeList);
        return this.queryForAllBean(MenuRoleMapping.class, sqlBuilder);
    }

    public Optional<MenuRoleMapping> findFirst(String roleCode, String menuCode){
        SqlBuilder sqlBuilder = SqlBuilder.init("select * from").joinDirect(this.getTable()).joinDirect("where role_code=?", roleCode)
                .joinDirect("where menu_code=?", menuCode);
        return this.findFirstBean(MenuRoleMapping.class, sqlBuilder);
    }
}
