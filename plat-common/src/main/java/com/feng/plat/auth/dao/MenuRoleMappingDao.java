package com.feng.plat.auth.dao;

import com.feng.home.common.base.BaseDao;
import com.feng.home.common.sql.SqlBuilder;
import com.feng.plat.auth.bean.MenuRoleMapping;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Component
public class MenuRoleMappingDao extends BaseDao {

    @Override
    @Resource
    protected void setTemplate(JdbcTemplate jdbcTemplate) {
        this.template = jdbcTemplate;
    }

    public List<MenuRoleMapping> getListByRoleCodeList(List<String> roleList){
        SqlBuilder.SqlResult sqlResult = new SqlBuilder(SqlBuilder.SqlTypeEnum.SELECT, "menu_role")
                .selectFor("*")
                .whereIn("role_code", roleList)
                .build();
        return this.queryForAllBean(MenuRoleMapping.class, sqlResult.sql, sqlResult.param);
    }

    public Optional<MenuRoleMapping> findFirst(String roleCode, String menuCode){
        SqlBuilder.SqlResult sqlResult = new SqlBuilder(SqlBuilder.SqlTypeEnum.SELECT, "menu_role")
                .selectFor("*")
                .whereEqual("role_code", roleCode)
                .whereEqual("menu_code", menuCode)
                .build();
        return this.findFirstBean(MenuRoleMapping.class, sqlResult.sql, sqlResult.param);
    }
}
