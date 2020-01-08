package com.feng.plat.user.dao;

import com.feng.home.common.jdbc.base.BaseMappingDao;
import com.feng.home.common.jdbc.base.DaoMapping;
import com.feng.home.common.sql.SqlBuilder;
import com.feng.home.plat.user.bean.UserRoleMapping;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Component
@DaoMapping(logicTable = "sys_user_role")
public class UserRoleMappingDao extends BaseMappingDao {

    public List<UserRoleMapping> getUserRoleMappingListByUserId(int user_id) {
        SqlBuilder sqlBuilder = SqlBuilder.init("select * from").joinDirect(this.getTable()).joinDirect("where user_id=?", user_id);
        return this.queryForAllBean(UserRoleMapping.class, sqlBuilder);
    }

    public List<UserRoleMapping> getUserRoleMappingListByUserIdList(List<Integer> idList){
        SqlBuilder sqlBuilder = SqlBuilder.init("select * from").joinDirect(this.getTable()).joinIn("where user_id", idList);
        return this.queryForAllBean(UserRoleMapping.class, sqlBuilder);
    }

    @Resource
    @Override
    protected void setJdbcTemplate(JdbcTemplate template) {
        this.jdbcTemplate = template;
    }
}
