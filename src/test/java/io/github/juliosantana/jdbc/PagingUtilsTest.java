package io.github.juliosantana.jdbc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import io.github.juliosantana.jdbc.PagingUtils;

@ExtendWith(MockitoExtension.class)
class PagingUtilsTest {

	@Test
    void testAddOrderBy() {
        // Cria um objeto Pageable com ordenação por nome e idade
        Pageable pageable = PageRequest.of(0, 10, Sort.by("nome", "idade"));
        // Cria um objeto StringBuilder com uma consulta SQL
        StringBuilder query = new StringBuilder("SELECT * FROM pessoa");
        // Chama o método addOrderBy da classe PagingUtils
        PagingUtils.addOrderBy(query, pageable);
        // Verifica se o resultado é o esperado
        assertEquals("SELECT * FROM pessoa ORDER BY nome ASC, idade ASC", query.toString());
    }

    @Test
    void testAddPaging() {
        // Cria um objeto Pageable com a segunda página de tamanho 5
        Pageable pageable = PageRequest.of(1, 5);
        // Cria um objeto StringBuilder com uma consulta SQL
        StringBuilder query = new StringBuilder("SELECT * FROM pessoa");
        // Chama o método addPaging da classe PagingUtils
        PagingUtils.addPaging(query, pageable);
        // Verifica se o resultado é o esperado
        assertEquals("SELECT * FROM pessoa OFFSET 5 ROWS FETCH NEXT 5 ROWS ONLY", query.toString());
    }

    @Test
    void testAddOrderByAndPaging() {
        // Cria um objeto Pageable com ordenação por nome e idade e a segunda página de tamanho 5
        Pageable pageable = PageRequest.of(1, 5, Sort.by("nome", "idade"));
        // Cria um objeto StringBuilder com uma consulta SQL
        StringBuilder query = new StringBuilder("SELECT * FROM pessoa");
        // Chama o método addOrderByAndPaging da classe PagingUtils
        PagingUtils.addOrderByAndPaging(query, pageable);
        // Verifica se o resultado é o esperado
        assertEquals("SELECT * FROM pessoa ORDER BY nome ASC, idade ASC OFFSET 5 ROWS FETCH NEXT 5 ROWS ONLY", query.toString());
    }

    @Test
    void testCreateDefaultCount() {
        // Cria uma string com uma consulta SQL
        String query = "SELECT * FROM pessoa";
        // Chama o método createDefaultCount da classe PagingUtils
        String count = PagingUtils.createDefaultCount(query);
        // Verifica se o resultado é o esperado
        assertEquals("SELECT COUNT(*) FROM ( SELECT * FROM pessoa )", count);
    }

}
