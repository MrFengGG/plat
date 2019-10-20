package com.feng.plat.auth.dao;

import com.feng.home.common.base.BaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ResourceRoleMappingDao extends BaseDao {

    @Override
    @Autowired
    protected void setTemplate(JdbcTemplate jdbcTemplate) {
        this.template = jdbcTemplate;
    }

}
