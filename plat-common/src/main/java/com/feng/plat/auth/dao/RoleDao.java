package com.feng.plat.auth.dao;

import com.feng.home.common.jdbc.base.BaseMappingDao;
import com.feng.home.common.jdbc.base.DaoMapping;
import com.feng.home.common.jdbc.pagination.Page;
import com.feng.home.common.sql.SqlBuilder;
import com.feng.home.plat.user.bean.condition.RoleQueryCondition;
import com.feng.home.plat.auth.bean.Role;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
@DaoMapping(logicTable = "role")
public class RoleDao extends BaseMappingDao{

    @Resource
    @Override
    protected void setJdbcTemplate(JdbcTemplate template) {
        this.jdbcTemplate = template;
    }

    public Optional<Role> findByCode(String roleCode) {
        SqlBuilder.SqlResult sqlResult = new SqlBuilder(SqlBuilder.SqlTypeEnum.SELECT, "role")
                .selectFor("*")
                .whereEqual("code", roleCode)
                .build();
        return this.findFirstBean(Role.class, sqlResult.sql, sqlResult.param);
    }

    public List<Role> getListByCodeList(Collection<String> roleCodeList) {
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
                .whereNotLess("create_time", roleQueryCondition.getCreateStartTime())
                .whereNotGrater("create_time", roleQueryCondition.getCreateEndTime())
                .whereNotLess("update_time", roleQueryCondition.getUpdateStartTime())
                .whereNotGrater("update_time", roleQueryCondition.getUpdateEndTime())
                .build();
        return this.pageQuery(roleQueryCondition, page);
    }
}
