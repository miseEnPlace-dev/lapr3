-- Localidades (verificaçao de restriçoes)
INSERT INTO Localidade (cod_postal,localidade) VALUES ('1000-00','Lisboa'); -- FALHA
INSERT INTO Localidade (cod_postal,localidade) VALUES ('1000-012',NULL); -- FALHA
INSERT INTO Localidade (cod_postal,localidade) VALUES (NULL,NULL); -- FALHA

-- Clientes (verificaçao de restriçoes)
INSERT INTO Cliente (nome,nif,email,morada,morada_entrega,plafond,cod_postal_entrega,cod_postal) VALUES (NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL); -- FALHA
INSERT INTO Cliente (nome,nif,email,morada,morada_entrega,plafond,cod_postal_entrega,cod_postal) VALUES ('eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee','12345678910','eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee','eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee','eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee','a','123-123','123-123'); -- FALHA

-- EscalaoIva (verificaçao de restriçoes)
INSERT INTO EscalaoIva (valor) VALUES (NULL); -- FALHA

-- Setor (verificaçao de restriçoes)
INSERT INTO Setor (designacao,area) VALUES (NULL,NULL); -- FALHA
INSERT INTO Setor (designacao,area) VALUES ('AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA',1234567891011); -- FALHA

-- Produtos (verificaçao de restriçoes)

