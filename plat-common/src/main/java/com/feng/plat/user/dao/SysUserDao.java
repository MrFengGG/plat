package com.feng.plat.user.dao;


import com.feng.home.common.auth.AuthContext;
import com.feng.home.common.jdbc.base.BaseMappingDao;
import com.feng.home.common.jdbc.base.DaoMapping;
import com.feng.home.common.jdbc.pagination.Page;
import com.feng.home.common.sql.SqlBuilder;
import com.feng.home.plat.user.bean.SysUser;
import com.feng.home.plat.user.bean.condition.UserQueryCondition;
import com.feng.home.plat.user.enums.UserStatusEnum;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
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
        SqlBuilder sqlBuilder = SqlBuilder.init("select * from ").joinDirect(this.getTable()).joinDirect("where username=?", username)
                .joinDirect("and status=?", UserStatusEnum.NORMAL.getCode());
        return this.findFirstBean(SysUser.class, sqlBuilder);
    }

    public Page<SysUser> pageQuery(UserQueryCondition userQueryCondition, Page<SysUser> page){
        SqlBuilder sqlBuilder = SqlBuilder.init("select * from ").joinDirect(this.getTable()).joinDirect("where 1=1")
                .joinLikeAround("and email", userQueryCondition.getEmail())
                .joinLikeAround("and mobile", userQueryCondition.getMobile())
                .joinLikeAround("and username", userQueryCondition.getUsername())
                .joinIn("and status", userQueryCondition.getStatusList())
                .join("and create_time>=?", userQueryCondition.getRegisterStartTime())
                .join("and create_time<=?", userQueryCondition.getRegisterEndTime())
                .joinDirect("and user_type > ?", AuthContext.getContextUser().getExtend().getInt("userType"));
        return this.queryForPaginationBean(page, SysUser.class, sqlBuilder);
    }

}
