-- Localidades
INSERT INTO Localidade (cod_postal,localidade) VALUES ('1234-567','Porto');
INSERT INTO Localidade (cod_postal,localidade) VALUES ('1234-566','Porto');
INSERT INTO Localidade (cod_postal,localidade) VALUES ('1234-565','Porto');

-- Clientes
INSERT INTO Cliente (nome,nif,email,morada,morada_entrega,plafond,cod_postal_entrega,cod_postal) VALUES ('Joao','123456789','email@gmail.com','Rua do Joao','Rua do Joao','1000','1234-567','1234-567');
INSERT INTO Cliente (nome,nif,email,morada,morada_entrega,plafond,cod_postal_entrega,cod_postal) VALUES ('Maria','987654321','maria@gmail.com','Rua da Maria','Rua da Maria','1000','1234-566','1234-566');
INSERT INTO Cliente (nome,nif,email,morada,morada_entrega,plafond,cod_postal_entrega,cod_postal) VALUES ('Jose','11111111','jose@gmail.com','Rua do Jose','Rua do Jose','1000','1234-565','1234-565');

-- Setores
INSERT INTO Setor (designacao,area) VALUES ('Setor 1','100');
INSERT INTO Setor (designacao,area) VALUES ('Setor 2','100');
INSERT INTO Setor (designacao,area) VALUES ('Setor 3','100');

-- EscalaoIva
INSERT INTO EscalaoIva (valor) VALUES (23);
INSERT INTO EscalaoIva (valor) VALUES (13);
INSERT INTO EscalaoIva (valor) VALUES (6);

-- Produtos
INSERT INTO Produto (designacao,preco,id_escalao_iva) VALUES ('Maça',2,2);
INSERT INTO Produto (designacao,preco,id_escalao_iva) VALUES ('Pera',2,2);
INSERT INTO Produto (designacao,preco,id_escalao_iva) VALUES ('Laranja',2,2);
INSERT INTO Produto (designacao,preco,id_escalao_iva) VALUES ('Cerejeira',10,1);
