package com.feng.plat.auth.dao;

import com.feng.home.common.jdbc.base.BaseMappingDao;
import com.feng.home.common.jdbc.base.DaoMapping;
import com.feng.home.common.sql.SqlBuilder;
import com.feng.home.plat.auth.bean.MenuGroup;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
@DaoMapping(logicTable = "menu_group")
public class MenuGroupDao extends BaseMappingDao{
    @Resource
    @Override
    protected void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<MenuGroup> getListByCodeList(List<String> codeList){
        SqlBuilder sqlBuilder = SqlBuilder.init("select * from").joinDirect(this.getTable()).joinIn("where code", codeList);
        return this.queryForAllBean(MenuGroup.class, sqlBuilder);
    }

    public List<MenuGroup> getAll(){
        SqlBuilder sqlBuilder = SqlBuilder.init("select * from").joinDirect(this.getTable());
        return this.queryForAllBean(MenuGroup.class, sqlBuilder);
    }
}
