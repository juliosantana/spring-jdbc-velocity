package io.github.juliosantana.jdbc;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

public interface VelocityJdbcTemplate {

	<T> Optional<T> queryForObject(String sql, SqlParameterSource paramSource, RowMapper<T> rowMapper)
			throws DataAccessException;
	
	<T> List<T> query(String sql, SqlParameterSource paramSource, RowMapper<T> rowMapper) throws DataAccessException;
	
	<T> Page<T> query(String query, SqlParameterSource paramSource, Pageable pageable, RowMapper<T> rowMapper)
			throws DataAccessException;

	<T> Page<T> query(String query, String queryCount, SqlParameterSource paramSource, Pageable pageable,
			RowMapper<T> rowMapper) throws DataAccessException;

}
