package io.github.juliosantana.jdbc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import io.github.juliosantana.jdbc.VelocityJdbcTemplate;
import io.github.juliosantana.jdbc.VelocityJdbcTemplateImpl;

@ExtendWith(MockitoExtension.class)
class VelocityJdbcTemplateIntegrationTest {
	
	static JdbcDataSource dataSource;
	Connection connection;
	
	VelocityJdbcTemplate velocityTemplate;
	
	
	@BeforeAll
    static void setUp() throws Exception {
		dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:testdb;MODE=Oracle;INIT=RUNSCRIPT FROM 'classpath:script.sql'");
        dataSource.setUser("sa");
        dataSource.setPassword("");
        dataSource.getConnection();
    }
	
	@BeforeEach
	void beforeEach() throws SQLException {
		connection = dataSource.getConnection();
		velocityTemplate = new VelocityJdbcTemplateImpl(dataSource);
	}
	
	@Test
	void shoulCountTen() {
		Optional<Long> count = velocityTemplate.queryForObject("SELECT COUNT(*) FROM PESSOA FETCH FIRST 10 ROWS", new MapSqlParameterSource(), new SingleColumnRowMapper<>(Long.class));
		assertTrue(count.isPresent());
		assertEquals(10, count.get());
	}

}
