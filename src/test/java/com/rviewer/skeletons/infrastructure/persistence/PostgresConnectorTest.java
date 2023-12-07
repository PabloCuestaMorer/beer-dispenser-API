package com.rviewer.skeletons.infrastructure.persistence;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PostgresConnectorTest {

    @Mock
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private PostgresConnector postgresConnector;

    @Test
    public void getConnectionStatus_shouldReturn2() {
        when(jdbcTemplate.queryForObject("SELECT 1+1", Integer.class)).thenReturn(2);
        assertEquals(2, postgresConnector.getConnectionStatus());
    }
}
