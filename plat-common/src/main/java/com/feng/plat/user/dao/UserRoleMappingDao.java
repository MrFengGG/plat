package com.feng.plat.user.dao;

import com.feng.home.common.base.BaseDao;
import com.feng.home.common.sql.SqlBuilder;
import com.feng.plat.user.bean.UserRoleMapping;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Component
public class UserRoleMappingDao extends BaseDao {

    public List<UserRoleMapping> getUserRoleMappingListByUserId(int user_id) {
        SqlBuilder.SqlResult sqlResult = new SqlBuilder(SqlBuilder.SqlTypeEnum.SELECT, "user_role")
                .selectFor("*")
                .whereEqual("user_id", user_id)
                .build();
        return this.queryForAllBean(UserRoleMapping.class, sqlResult.sql, sqlResult.param);
    }

    public List<UserRoleMapping> getUserRoleMappingListByUserIdList(List<Integer> idList){
        SqlBuilder.SqlResult sqlResult = new SqlBuilder(SqlBuilder.SqlTypeEnum.SELECT, "user_role")
                .selectFor("*")
                .whereIn("user_id", idList)
                .build();
        return this.queryForAllBean(UserRoleMapping.class, sqlResult.sql, sqlResult.param);
    }

    public Optional<UserRoleMapping> findUniqueUserRoleMapping(int userId, String roleCode){
        SqlBuilder.SqlResult sqlResult = new SqlBuilder(SqlBuilder.SqlTypeEnum.SELECT, "user_role")
                .selectFor("*")
                .whereEqual("user_id", userId)
                .whereEqual("role_code", roleCode)
                .build();
        return this.findFirstBean(UserRoleMapping.class, sqlResult.sql, sqlResult.param);
    }

    @Resource
    @Override
    protected void setTemplate(JdbcTemplate template) {
        this.template = template;
    }
}
