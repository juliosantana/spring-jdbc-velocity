package br.com.spring.data.velocity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

@ExtendWith(MockitoExtension.class)
class QueryParserTest {

	QueryParser parser = new QueryParser();

	@Test
	void testParserStringWithoutParams() {
		String value = "Test without param";
		String parsed = parser.parser(value, new MapSqlParameterSource());
		assertEquals(value, parsed);
	}

	@Test
	void testParserStringWithParams() {
		String value = "Test without param: #if ( $name ) PARAM OK #end";
		String parsed = parser.parser(value, new MapSqlParameterSource("name", "A"));
		assertEquals("Test without param:  PARAM OK ", parsed);
	}
	
	@Test
	void testParserStringWithParamsWithoutSqlParam() {
		String value = "Test without param: #if ( $name ) PARAM OK #end";
		String parsed = parser.parser(value, new MapSqlParameterSource());
		assertEquals("Test without param: ", parsed);
	}

}
