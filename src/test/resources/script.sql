-- Cria a tabela pessoa
CREATE TABLE IF NOT EXISTS PESSOA (
    ID INT PRIMARY KEY,
    NOME VARCHAR(255),
    IDADE INT
);

DELETE FROM PESSOA;

-- Insere alguns dados na tabela pessoa
INSERT INTO PESSOA VALUES (1, 'João');
INSERT INTO PESSOA VALUES (2, 'Maria');
INSERT INTO PESSOA VALUES (3, 'Pedro');