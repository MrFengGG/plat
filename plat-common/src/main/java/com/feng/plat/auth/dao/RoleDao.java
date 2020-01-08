package com.feng.plat.auth.dao;

import com.feng.home.common.auth.AuthContext;
import com.feng.home.common.jdbc.base.BaseMappingDao;
import com.feng.home.common.jdbc.base.DaoMapping;
import com.feng.home.common.jdbc.pagination.Page;
import com.feng.home.common.sql.SqlBuilder;
import com.feng.home.plat.auth.bean.condition.RoleQueryCondition;
import com.feng.home.plat.auth.bean.Role;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
@DaoMapping(logicTable = "sys_role")
public class RoleDao extends BaseMappingDao{

    @Resource
    @Override
    protected void setJdbcTemplate(JdbcTemplate template) {
        this.jdbcTemplate = template;
    }

    public Optional<Role> findByCode(String roleCode) {
        SqlBuilder sqlBuilder = SqlBuilder.init("select * from").joinDirect(this.getTable()).joinDirect("where code=?", roleCode);
        return this.findFirstBean(Role.class, sqlBuilder);
    }

    public List<Role> getListByCodeList(Collection<String> roleCodeList) {
        SqlBuilder sqlBuilder = SqlBuilder.init("select * from").joinDirect(this.getTable()).joinIn("where code", roleCodeList);
        return this.queryForAllBean(Role.class, sqlBuilder);
    }

    public Page<Role> pageQuery(RoleQueryCondition roleQueryCondition, Page<Role> page){
        SqlBuilder sqlBuilder = SqlBuilder.init("select * from").joinDirect(this.getTable()).joinDirect("where 1=1")
                .joinLikeAround("and role_name", roleQueryCondition.getRoleName())
                .joinLikeAround("and role_desc", roleQueryCondition.getRoleDesc())
                .join("and role_desc", roleQueryCondition.getRoleDesc())
                .join("and create_time>=", roleQueryCondition.getCreateStartTime())
                .join("and create_time<=", roleQueryCondition.getCreateEndTime())
                .join("and update_time>=", roleQueryCondition.getUpdateStartTime())
                .join("and update_time<=", roleQueryCondition.getUpdateEndTime())
                .joinDirect("and user_type > ?", AuthContext.getContextUser().getExtend().getInt("user_type"));
        return this.queryForPaginationBean(page, Role.class, sqlBuilder);
    }

    public List<Role> getAll(){
        SqlBuilder sqlBuilder = SqlBuilder.init("select * from").joinDirect(this.getTable())
                .joinDirect("where user_type > ?", AuthContext.getContextUser().getExtend().getInt("user_type"));;
        return this.queryForAllBean(Role.class, sqlBuilder);
    }
}
