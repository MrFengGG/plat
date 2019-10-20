package com.feng.plat.auth.dao;

import com.feng.home.common.base.BaseDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ResourceDao extends BaseDao {
    @Override
    protected void setTemplate(JdbcTemplate jdbcTemplate) {
        this.template = jdbcTemplate;
    }
}
