package com.feng.plat.auth.dao;

import com.feng.home.common.base.BaseDao;
import com.feng.home.common.sql.SqlBuilder;
import com.feng.plat.auth.bean.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MenuDao extends BaseDao {

    @Override
    @Autowired
    protected void setTemplate(JdbcTemplate jdbcTemplate) {
        this.template = jdbcTemplate;
    }

    public List<Menu> getMenuListByCodeList(List<String> menuCodeList){
        SqlBuilder.SqlResult sqlResult = new SqlBuilder(SqlBuilder.SqlTypeEnum.SELECT, "menu")
                .selectFor("*")
                .whereIn("code", menuCodeList)
                .build();
        return this.queryForAllBean(Menu.class, sqlResult.sql, sqlResult.param);
    }
}
