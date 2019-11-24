package com.feng.plat.auth.dao;

import com.feng.home.common.jdbc.base.BaseMappingDao;
import com.feng.home.common.jdbc.base.DaoMapping;
import com.feng.home.common.jdbc.pagination.Page;
import com.feng.home.common.sql.SqlBuilder;
import com.feng.home.plat.auth.bean.Menu;
import com.feng.home.plat.auth.bean.condition.MenuQueryCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@DaoMapping(logicTable = "menu")
public class MenuDao extends BaseMappingDao{

    @Override
    @Autowired
    protected void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 根据菜单代码列表查询菜单
     * @param menuCodeList
     * @return
     */
    public List<Menu> getListByGroupCodeList(String group, List<String> menuCodeList){
        SqlBuilder.SqlResult sqlResult = new SqlBuilder(SqlBuilder.SqlTypeEnum.SELECT, this.getTable())
                .selectFor("*")
                .whereEqual("menu_group_code", group)
                .whereIn("code", menuCodeList)
                .orderBy("priority", SqlBuilder.OrderTypeEnum.DESC)
                .build();
        return this.queryForAllBean(Menu.class, sqlResult.sql, sqlResult.param);
    }

    /**
     * 根据菜单代码列表查询菜单
     * @param menuCodeList
     * @return
     */
    public List<Menu> getListByCodeList(List<String> menuCodeList){
        SqlBuilder.SqlResult sqlResult = new SqlBuilder(SqlBuilder.SqlTypeEnum.SELECT, this.getTable())
                .selectFor("*")
                .whereIn("code", menuCodeList)
                .build();
        return this.queryForAllBean(Menu.class, sqlResult.sql, sqlResult.param);
    }

    public Page<Menu> pageQuery(MenuQueryCondition menuQueryCondition, Page<Menu> page){
        SqlBuilder.SqlResult sqlResult = new SqlBuilder(SqlBuilder.SqlTypeEnum.SELECT, this.getTable())
                .selectFor("*")
                .build();
        return this.queryForPaginationBean(page, Menu.class, sqlResult.sql, sqlResult.param);
    }


    /**
     * 获取所有菜单
     * @return
     */
    public List<Menu> getAllMenuList(){
        SqlBuilder.SqlResult sqlResult = new SqlBuilder(SqlBuilder.SqlTypeEnum.SELECT, this.getTable())
                .selectFor("*")
                .orderBy("priority", SqlBuilder.OrderTypeEnum.DESC)
                .build();
        return this.queryForAllBean(Menu.class, sqlResult.sql, sqlResult.param);
    }

    /**
     * 根据菜单代码查询菜单
     * @param code
     * @return
     */
    public Optional<Menu> findByCode(String code){
        SqlBuilder.SqlResult sqlResult = new SqlBuilder(SqlBuilder.SqlTypeEnum.SELECT, this.getTable())
                .selectFor("*")
                .whereEqual("code", code)
                .build();
        return this.findFirstBean(Menu.class, sqlResult.sql, sqlResult.param);
    }

    /**
     * 根据菜单代码删除菜单
     * @param code
     */
    public void removeByCode(String code){
        SqlBuilder.SqlResult sqlResult = new SqlBuilder(SqlBuilder.SqlTypeEnum.REMOVE, this.getTable())
                .whereEqual("code", code)
                .build();
        this.jdbcTemplate.update(sqlResult.sql, sqlResult.param);
    }
}
