package com.feng.plat.auth.dao;

import com.feng.home.common.collection.CollectionUtils;
import com.feng.home.common.common.StringUtil;
import com.feng.home.common.enums.Binary;
import com.feng.home.common.exception.SampleBusinessException;
import com.feng.home.common.jdbc.base.BaseMappingDao;
import com.feng.home.common.jdbc.base.DaoMapping;
import com.feng.home.common.sql.SqlBuilder;
import com.feng.home.common.validate.AssertUtil;
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
@DaoMapping(logicTable = "sys_menu")
public class MenuDao extends BaseMappingDao{

    @Override
    @Autowired
    protected void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Menu> getEnableListByGroupCodeList(String group, List<String> menuCodeList){
        MenuQueryCondition condition = MenuQueryCondition.builder().menuCodeList(menuCodeList)
                .menuGroupCode(group)
                .enable(Binary.YES.getValue())
                .build();
        return query(condition);
    }

    public List<Menu> getEnableListByCodeList(List<String> menuCodeList){
        MenuQueryCondition condition = MenuQueryCondition.builder().menuCodeList(menuCodeList)
                .enable(Binary.YES.getValue())
                .build();
        return this.query(condition);
    }

    public List<Menu> getEnableList(){
        SqlBuilder sqlBuilder = SqlBuilder.init("select * from").joinDirect(this.getTable())
                .joinDirect("where enable=?", 1)
                .joinDirect("order by priority desc");
        return this.queryForAllBean(Menu.class, sqlBuilder);
    }

    public Optional<Menu> findByCode(String code){
        return this.findBy("code", Menu.class, this.getTable(), code);
    }

    public int removeByPath(String path){
        if(StringUtil.isEmpty(path)){
            throw new SampleBusinessException("路径为空,无法删除");
        }
        SqlBuilder sqlBuilder = SqlBuilder.init("delete from").joinDirect(this.getTable()).joinLikeAfter("where tree_path", path);
        return this.update(sqlBuilder);
    }

    public List<Menu> query(MenuQueryCondition condition){
        Date[] timeTuple = condition.getCreateTimeTuple();
        SqlBuilder sqlBuilder = SqlBuilder.init("select * from").joinDirect(this.getTable())
                .joinDirect("where 1=1").joinLikeAround("and menu_name", condition.getMenuName())
                .joinLikeAround("and menu_path", condition.getMenuPath())
                .join("and create_time >= ", CollectionUtils.safeGet(timeTuple,0))
                .join("and create_time <= ", CollectionUtils.safeGet(timeTuple,1))
                .join("and enable =?", condition.getEnable())
                .join("and menu_group_code =?", condition.getMenuGroupCode())
                .join("and parent_code =?", condition.getParentCode()).joinDirect("order by priority desc");
        return this.queryForAllBean(Menu.class, sqlBuilder);
    }
}
