package com.feng.plat.auth.dao;

import com.feng.home.common.base.BaseDao;
import com.feng.home.common.pagination.Page;
import com.feng.home.common.sql.SqlBuilder;
import com.feng.home.plat.user.bean.RoleQueryCondition;
import com.feng.plat.auth.bean.Role;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
public class RoleDao extends BaseDao {

    @Resource
    @Override
    protected void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

    public Optional<Role> findRoleByCode(String roleCode) {
        SqlBuilder.SqlResult sqlResult = new SqlBuilder(SqlBuilder.SqlTypeEnum.SELECT, "role")
                .selectFor("*")
                .whereEqual("code", roleCode)
                .build();
        return this.findFirstBean(Role.class, sqlResult.sql, sqlResult.param);
    }

    public List<Role> getRoleListByCodeList(Collection<String> roleCodeList) {
        SqlBuilder.SqlResult sqlResult = new SqlBuilder(SqlBuilder.SqlTypeEnum.SELECT, "role")
                .selectFor("*")
                .whereIn("code", roleCodeList)
                .build();
        return this.queryForAllBean(Role.class, sqlResult.sql, sqlResult.param);
    }

    public Page<Role> pageQuery(RoleQueryCondition roleQueryCondition, Page<Role> page){
        SqlBuilder.SqlResult sqlResult = new SqlBuilder(SqlBuilder.SqlTypeEnum.SELECT, "role")
                .selectFor("*")
                .whereLike("role_name", roleQueryCondition.getRoleName())
                .whereLike("role_desc", roleQueryCondition.getRoleDesc())
                .whereGraterIfNotEmpty("create_time", roleQueryCondition.getCreateStartTime())
                .whereLessIfNotEmpty("create_time", roleQueryCondition.getCreateEndTime())
                .whereGraterIfNotEmpty("update_time", roleQueryCondition.getUpdateStartTime())
                .whereLessIfNotEmpty("update_time", roleQueryCondition.getUpdateEndTime())
                .build();
        return this.pageQuery(roleQueryCondition, page);
    }
}
