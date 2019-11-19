package com.feng.plat.auth.dao;

import com.feng.home.common.jdbc.base.BaseMappingDao;
import com.feng.home.common.jdbc.base.DaoMapping;
import com.feng.home.common.sql.SqlBuilder;
import com.feng.home.plat.auth.bean.MenuGroup;
import com.feng.home.plat.auth.bean.condition.MenuQueryCondition;
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
        SqlBuilder.SqlResult sqlResult = new SqlBuilder(SqlBuilder.SqlTypeEnum.SELECT, this.getTable())
                .selectFor("*")
                .whereIn("code", codeList)
                .build();
        return this.queryForAllBean(MenuGroup.class, sqlResult.sql, sqlResult.param);
    }

    public List<MenuGroup> getAll(){
        SqlBuilder.SqlResult sqlResult = new SqlBuilder(SqlBuilder.SqlTypeEnum.SELECT, this.getTable())
                .selectFor("*")
                .build();
        return this.queryForAllBean(MenuGroup.class, sqlResult.sql, sqlResult.param);
    }
}
