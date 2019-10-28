package com.feng.plat.auth.dao;

import com.feng.home.common.base.BaseDao;
import com.feng.home.common.sql.SqlBuilder;
import com.feng.plat.auth.bean.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class MenuDao extends BaseDao {

    @Override
    @Autowired
    protected void setTemplate(JdbcTemplate jdbcTemplate) {
        this.template = jdbcTemplate;
    }

    /**
     * 根据菜单代码列表查询菜单
     * @param menuCodeList
     * @return
     */
    public List<Menu> getMenuListByCodeList(List<String> menuCodeList){
        SqlBuilder.SqlResult sqlResult = new SqlBuilder(SqlBuilder.SqlTypeEnum.SELECT, "menu")
                .selectFor("*")
                .whereIn("code", menuCodeList)
                .build();
        return this.queryForAllBean(Menu.class, sqlResult.sql, sqlResult.param);
    }

    /**
     * 获取所有菜单
     * @return
     */
    public List<Menu> getAllMenuList(){
        SqlBuilder.SqlResult sqlResult = new SqlBuilder(SqlBuilder.SqlTypeEnum.SELECT, "menu")
                .selectFor("*")
                .build();
        return this.queryForAllBean(Menu.class, sqlResult.sql, sqlResult.param);
    }

    /**
     * 根据菜单代码查询菜单
     * @param code
     * @return
     */
    public Optional<Menu> findMenuByCode(String code){
        SqlBuilder.SqlResult sqlResult = new SqlBuilder(SqlBuilder.SqlTypeEnum.SELECT, "menu")
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
        SqlBuilder.SqlResult sqlResult = new SqlBuilder(SqlBuilder.SqlTypeEnum.REMOVE, "menu")
                .whereEqual("code", code)
                .build();
        this.update(sqlResult.sql, sqlResult.param);
    }
}
