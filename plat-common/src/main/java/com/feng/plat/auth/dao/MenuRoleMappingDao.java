package com.feng.plat.auth.dao;

import com.feng.home.common.base.BaseDao;
import com.feng.home.common.sql.SqlBuilder;
import com.feng.plat.auth.bean.MenuRoleMapping;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

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
}
