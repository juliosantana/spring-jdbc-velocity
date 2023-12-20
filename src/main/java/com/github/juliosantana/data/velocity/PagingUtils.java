package com.github.juliosantana.data.velocity;

import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;

class PagingUtils {

	private static final String COMMA_SEPARADOR = ", ";
	private static final String ORDER = " ORDER BY %s";
	private static final String PAGINATION = " OFFSET %d ROWS FETCH NEXT %d ROWS ONLY";
	private static final String DEFAULT_COUNT = "SELECT COUNT(*) FROM ( %s )";

	static void addOrderBy(final StringBuilder query, Pageable pageable) {

		String sort = pageable.getSort().get()
				.map(o -> StringUtils.join(o.getProperty(), StringUtils.SPACE, o.getDirection().name()))
				.collect(Collectors.joining(COMMA_SEPARADOR));

		query.append(String.format(ORDER, sort));
	}

	static void addPaging(final StringBuilder query, Pageable pageable) {
		query.append(String.format(PAGINATION, pageable.getOffset(), pageable.getPageSize()));
	}

	static void addOrderByAndPaging(final StringBuilder query, Pageable pageable) {
		addOrderBy(query, pageable);
		addPaging(query, pageable);
	}
	
	static String createDefaultCount(final String query) {
		return String.format(DEFAULT_COUNT, query);
	}

}
