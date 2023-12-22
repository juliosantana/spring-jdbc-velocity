package io.github.juliosantana.jdbc;

import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import io.github.juliosantana.velocity.QueryParser;

public class VelocityJdbcTemplateImpl implements VelocityJdbcTemplate  {
	
	private QueryParser queryParser;
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	public VelocityJdbcTemplateImpl(NamedParameterJdbcTemplate namedJdbcTemplate, QueryParser queryParser) {
		this.namedJdbcTemplate = namedJdbcTemplate;
		this.queryParser = queryParser;
	}
	
	public VelocityJdbcTemplateImpl(DataSource dataSource) {
		this(new NamedParameterJdbcTemplate(dataSource));
	}

	public VelocityJdbcTemplateImpl(NamedParameterJdbcTemplate namedJdbcTemplate) {
		this(namedJdbcTemplate, new QueryParser());
	}

	@Override
	public <T> List<T> query(String sql, SqlParameterSource paramSource, RowMapper<T> rowMapper) throws DataAccessException{
		return namedJdbcTemplate.query(queryParser.parser(sql, paramSource), paramSource, rowMapper);
	}

	@Override
	public <T> Optional<T> queryForObject(String sql, SqlParameterSource paramSource, RowMapper<T> rowMapper)
			throws DataAccessException {
		return Optional.ofNullable(namedJdbcTemplate.queryForObject(queryParser.parser(sql, paramSource), paramSource, rowMapper));
	}

	@Override
	public <T> Page<T> query(String query, SqlParameterSource paramSource, Pageable pageable, RowMapper<T> rowMapper)
			throws DataAccessException {
		String queryCount = PagingUtils.createDefaultCount(query);
		return query(query, queryCount, paramSource, pageable, rowMapper);
	}

	@Override
	public <T> Page<T> query(String query, String queryCount, SqlParameterSource paramSource, Pageable pageable,
			RowMapper<T> rowMapper) throws DataAccessException {
		
		StringBuilder querySb = new StringBuilder(query);
		PagingUtils.addOrderByAndPaging(querySb, pageable);

		List<T> results = query(querySb.toString(), paramSource, rowMapper);

		Long count;
		if ((results.size() < pageable.getPageSize() && pageable.getOffset() == 0)
				|| (CollectionUtils.isEmpty(results))) {
			count = Long.valueOf(results.size());
		} else {
			count = queryForObject(queryCount, paramSource, new SingleColumnRowMapper<>(Long.class)).orElse(0L);
		}

		return new PageImpl<>(results, pageable, count);
	}
	

}
