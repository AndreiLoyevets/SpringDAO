package com.epam.andrii_loievets.springdao.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Andrii_Loievets
 * @param <T>
 */
public class GenericRepositoryJDBC<T> {
    protected JdbcTemplate jdbcTemplate;
    protected RowMapper<T> rowMapper;
    
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
