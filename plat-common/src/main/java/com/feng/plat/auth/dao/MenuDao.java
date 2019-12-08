package com.feng.plat.auth.dao;

import com.feng.home.common.collection.CollectionUtils;
import com.feng.home.common.enums.Binary;
import com.feng.home.common.jdbc.base.BaseMappingDao;
import com.feng.home.common.jdbc.base.DaoMapping;
import com.feng.home.common.sql.SqlBuilder;
import com.feng.home.plat.auth.bean.Menu;
import com.feng.home.plat.auth.bean.condition.MenuQueryCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;
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
    public List<Menu> getEnableListByGroupCodeList(String group, List<String> menuCodeList){
        MenuQueryCondition condition = MenuQueryCondition.builder().menuCodeList(menuCodeList)
                .menuGroupCode(group)
                .enable(Binary.YES.getValue())
                .build();
        return query(condition);
    }

    /**
     * 根据菜单代码列表查询菜单
     * @param menuCodeList
     * @return
     */
    public List<Menu> getEnableListByCodeList(List<String> menuCodeList){
        MenuQueryCondition condition = MenuQueryCondition.builder().menuCodeList(menuCodeList)
                .enable(Binary.YES.getValue())
                .build();
        return this.query(condition);
    }

    /**
     * 获取所有菜单
     * @return
     */
    public List<Menu> getEnableList(){
        SqlBuilder.SqlResult sqlResult = new SqlBuilder(SqlBuilder.SqlTypeEnum.SELECT, this.getTable())
                .selectFor("*")
                .whereNotLess("enable", 1)
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
     * @param codeList
     */
    public void removeByCode(Collection<String> codeList){
        SqlBuilder.SqlResult sqlResult = new SqlBuilder(SqlBuilder.SqlTypeEnum.REMOVE, this.getTable())
                .whereIn("code", codeList)
                .build();
        this.jdbcTemplate.update(sqlResult.sql, sqlResult.param);
    }

    public List<Menu> query(MenuQueryCondition condition){
        Date[] timeTuple = condition.getCreateTimeTuple();
        SqlBuilder.SqlResult sqlResult = new SqlBuilder(SqlBuilder.SqlTypeEnum.SELECT, this.getTable())
                .selectFor("*")
                .whereLikeAround("menu_name", condition.getMenuName())
                .whereLikeAround("menu_path", condition.getMenuPath())
                .whereNotLess("create_time", CollectionUtils.saveGet(timeTuple,0))
                .whereNotGrater("create_time", CollectionUtils.saveGet(timeTuple,1))
                .whereEqual("enable", condition.getEnable())
                .whereEqual("menu_group_code", condition.getMenuGroupCode())
                .orderBy("priority", SqlBuilder.OrderTypeEnum.DESC)
                .build();
        return this.queryForAllBean(Menu.class, sqlResult.sql, sqlResult.param);
    }
}
