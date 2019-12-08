package com.feng.plat.user.dao;


import com.feng.home.common.jdbc.base.BaseMappingDao;
import com.feng.home.common.jdbc.base.DaoMapping;
import com.feng.home.common.jdbc.pagination.Page;
import com.feng.home.common.sql.SqlBuilder;
import com.feng.home.plat.user.bean.SysUser;
import com.feng.home.plat.user.bean.condition.UserQueryCondition;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.Optional;

@Component
@DaoMapping(logicTable = "sys_user")
public class SysUserDao extends BaseMappingDao {
    @Override
    @Resource
    protected void setJdbcTemplate(JdbcTemplate template) {
        this.jdbcTemplate = template;
    }

    public Optional<SysUser> findUserByUsername(String username){
        SqlBuilder.SqlResult sqlResult = new SqlBuilder(SqlBuilder.SqlTypeEnum.SELECT, "sys_user")
                .selectFor("*")
                .whereEqual("username", username)
                .build();
        return this.findFirstBean(SysUser.class, sqlResult.sql, sqlResult.param);
    }

    public Page<SysUser> pageQuery(UserQueryCondition userQueryCondition, Page<SysUser> page) throws SQLException {
        SqlBuilder.SqlResult sqlResult = new SqlBuilder(SqlBuilder.SqlTypeEnum.SELECT, "sys_user")
                .selectFor("*")
                .whereEqual("email", userQueryCondition.getEmail())
                .whereEqual("mobile", userQueryCondition.getMobile())
                .whereLike("real_name", userQueryCondition.getRealName())
                .whereLike("username", userQueryCondition.getUsername())
                .whereIn("status", userQueryCondition.getStatusList())
                .whereNotLess("create_time", userQueryCondition.getRegisterStartTime())
                .whereNotGrater("create_time", userQueryCondition.getRegisterEndTime())
                .build();
        return this.queryForPaginationBean(page, SysUser.class, sqlResult.sql, sqlResult.param);
    }

}
