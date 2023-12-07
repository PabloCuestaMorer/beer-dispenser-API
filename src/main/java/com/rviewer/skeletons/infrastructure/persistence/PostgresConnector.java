package com.rviewer.skeletons.infrastructure.persistence;

import com.rviewer.skeletons.domain.services.persistence.DatabaseConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class PostgresConnector implements DatabaseConnector {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public int getConnectionStatus() {
        return jdbcTemplate.queryForObject("SELECT 1+1", Integer.class);
    }

}
