package com.feng.plat.user.dao;


import com.feng.home.common.base.BaseDao;
import com.feng.home.common.pagination.Page;
import com.feng.home.common.sql.SqlBuilder;
import com.feng.plat.user.bean.SysUser;
import com.feng.home.plat.user.bean.UserQueryCondition;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.Optional;

@Component
public class SysUserDao extends BaseDao {
    @Override
    @Resource
    protected void setTemplate(JdbcTemplate template) {
        this.template = template;
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
                .whereEqualIfNotEmpty("email", userQueryCondition.getEmail())
                .whereEqualIfNotEmpty("mobile", userQueryCondition.getMobile())
                .whereLike("real_name", userQueryCondition.getRealName())
                .whereLike("username", userQueryCondition.getUsername())
                .whereIn("status", userQueryCondition.getStatusList())
                .whereGraterIfNotEmpty("create_time", userQueryCondition.getRegisterStartTime())
                .whereLessIfNotEmpty("create_time", userQueryCondition.getRegisterEndTime())
                .build();
        return this.queryForPaginationBean(page, SysUser.class, sqlResult.sql, sqlResult.param);
    }

}
