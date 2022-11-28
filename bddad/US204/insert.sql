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

-- Encomendas
INSERT INTO Encomenda (id_cliente,data_vencimento_pagamento,data_registo,data_entrega,data_pagamento,morada_entrega,cod_postal_entrega) VALUES (1,'2017-12-12','2017-12-12','2017-12-12','2017-12-12','Rua do Joao','1234-567');
INSERT INTO Encomenda (id_cliente,data_vencimento_pagamento,data_registo,data_entrega,data_pagamento,morada_entrega,cod_postal_entrega) VALUES (2,'2017-12-12','2017-12-12','2017-12-12','2017-12-12','Rua da Maria','1234-566');
INSERT INTO Encomenda (id_cliente,data_vencimento_pagamento,data_registo,data_entrega,data_pagamento,morada_entrega,cod_postal_entrega) VALUES (3,'2017-12-12','2017-12-12','2017-12-12','2017-12-12','Rua do Jose','1234-565');


-- ProdutoEncomenda
INSERT INTO ProdutoEncomenda (id_encomenda,id_produto,quantidade,preco_unitario,iva,designacao_produto) VALUES (1,1,500,2,13,'Maça');
INSERT INTO ProdutoEncomenda (id_encomenda,id_produto,quantidade,preco_unitario,iva,designacao_produto) VALUES (2,2,1000,2,13,'Pera');
INSERT INTO ProdutoEncomenda (id_encomenda,id_produto,quantidade,preco_unitario,iva,designacao_produto) VALUES (3,3,2000,2,13,'Laranja');
INSERT INTO ProdutoEncomenda (id_encomenda,id_produto,quantidade,preco_unitario,iva,designacao_produto) VALUES (4,4,3000,10,23,'Cerejeira');

-- TipoCulturas
INSERT INTO TipoCultura (tipo_cultura) VALUES ('Maças');
INSERT INTO TipoCultura (tipo_cultura) VALUES ('Peras');
INSERT INTO TipoCultura (tipo_cultura) VALUES ('Laranjas');
INSERT INTO TipoCultura (tipo_cultura) VALUES ('Cerejeiras');

-- Culturas
INSERT INTO Cultura (cultura,id_tipo_cultura,id_produto) VALUES ('Maças',1,1);
INSERT INTO Cultura (cultura,id_tipo_cultura,id_produto) VALUES ('Pera',1,2);
INSERT INTO Cultura (cultura,id_tipo_cultura,id_produto) VALUES ('Laranja',1,3);
INSERT INTO Cultura (cultura,id_tipo_cultura,id_produto) VALUES ('Cerejeira',1,4);

-- TipoTubagem
INSERT INTO TipoTubagem (tipo_tubagem) VALUES ('Tubagem 1');
INSERT INTO TipoTubagem (tipo_tubagem) VALUES ('Tubagem 2');

-- TipoRega
INSERT INTO TipoRega (id_tipo_tubagem) VALUES (1);
INSERT INTO TipoRega (id_tipo_tubagem) VALUES (2);

-- Rega
INSERT INTO Rega (id_setor,data,id_tipo_rega) VALUES (1,'2017-12-12',1);
INSERT INTO Rega (id_setor,data,id_tipo_rega) VALUES (2,'2017-12-12',1);

-- PlanoRega
INSERT INTO PlanoRega (id_setor,data_inicio,tempo,periodicidade,id_tipo_rega,data_fim) VALUES (1,'2017-12-12',1,1,1,'2017-12-12');
