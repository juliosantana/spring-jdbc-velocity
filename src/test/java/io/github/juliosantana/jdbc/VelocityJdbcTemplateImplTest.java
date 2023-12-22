package io.github.juliosantana.jdbc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import io.github.juliosantana.jdbc.VelocityJdbcTemplateImpl;
import io.github.juliosantana.velocity.QueryParser;

@ExtendWith(MockitoExtension.class)
class VelocityJdbcTemplateImplTest {
	
	@InjectMocks
	VelocityJdbcTemplateImpl velocityJdbcTemplate;
	
	@Mock
	QueryParser queryParser;
	
	@Mock
	NamedParameterJdbcTemplate namedJdbcTemplate;

	@Test
	void testQueryForObject() {
	    // Cria um objeto SqlParameterSource com os parâmetros da consulta
	    SqlParameterSource paramSource = new MapSqlParameterSource("id", 1);
	    // Cria um objeto RowMapper para mapear o resultado da consulta
	    RowMapper<String> rowMapper = (rs, rowNum) -> rs.getString("nome");
	    // Cria um objeto String com a consulta SQL
	    String sql = "SELECT nome FROM pessoa WHERE id = :id";
	    // Cria um objeto String com o resultado esperado da consulta
	    String expected = "João";
	    // Define o comportamento do objeto simulado queryParser
	    when(queryParser.parser(sql, paramSource)).thenReturn(sql);
	    // Define o comportamento do objeto simulado namedJdbcTemplate
	    when(namedJdbcTemplate.queryForObject(sql, paramSource, rowMapper)).thenReturn(expected);
	    // Chama o método queryForObject da classe VelocityJdbcTemplateImpl
	    Optional<String> result = velocityJdbcTemplate.queryForObject(sql, paramSource, rowMapper);
	    // Verifica se o resultado é igual ao esperado
	    assertEquals(Optional.of(expected), result);
	}

}
