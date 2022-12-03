-- Teste especifico para as restrições das tabelas
INSERT INTO Setor (id_setor,designacao,area) VALUES (1,'Designacao',-1);
-- Input invalido (area >= 0)

INSERT INTO EscalaoIva (id_escalao_iva,valor) VALUES (-1,10);
-- Input invalido(valor >= 0)

INSERT INTO Produto (id_produto,designacao,preco,id_escalao_iva) VALUES (1,'alface', -1,1);
-- Input invalido(preco >= 0)

INSERT INTO Substancia (id_substancia,substancia) VALUES (1,'aaa');
INSERT INTO FatorProducaoSubstancia (id_fator_producao,id_substancia,percentagem) VALUES (1,1,10);
-- Input valido para testar as restrições
INSERT INTO FatorProducaoSubstancia (id_fator_producao,id_substancia,percentagem) VALUES (1,1,-1);
-- Input invalido(percentagem > 0)
INSERT INTO FatorProducaoSubstancia (id_fator_producao,id_substancia,percentagem) VALUES (1,1,101);
-- Input invalido(percentagem <= 100)

INSERT INTO PlanoRega (id_setor,data_inicio,tempo,periodicidade,id_tipo_rega,data_fim) VALUES (1,'2020-01-01',-1,10,1,null);
-- Input invalido(tempo > 0)
INSERT INTO PlanoRega (id_setor,data_inicio,tempo,periodicidade,id_tipo_rega,data_fim) VALUES (1,'2020-01-01',1,-1,1,null);
-- Input invalido(periodicidade > 0)

INSERT INTO Sensor (id_sensor,identificador,id_tipo_sensor,valor_referencia) VALUES (1,'sensor 1',1,-1);
-- Input invalido(valor_referencia > 0)

INSERT INTO Localidade (cod_postal,localidade) VALUES ('1-1','Porto');
-- Input invalido(cod_postal 'XXXX-XXX')

INSERT INTO Cliente (id_cliente,nome,nif,email,morada,morada_entrega,plafond,cod_postal_entrega,cod_postal,n_encomendas,valor_total_encomendas) VALUES (1,'João',12,'email@email.com','morada','morada',10,'1234-123','1234-123',10,100);
-- Input invalido(nif > 100000000 AND nif < 999999999)
INSERT INTO Cliente (id_cliente,nome,nif,email,morada,morada_entrega,plafond,cod_postal_entrega,cod_postal,n_encomendas,valor_total_encomendas) VALUES (1,'Joao',12345678910,'email@email.com','morada','morada',10,'1234-123','1234-123',10,100);
-- Input invalido(nif > 100000000 AND nif < 999999999)
INSERT INTO Cliente (id_cliente,nome,nif,email,morada,morada_entrega,plafond,cod_postal_entrega,cod_postal,n_encomendas,valor_total_encomendas) VALUES (1,'Joao',123456789,'emailemail.com','morada','morada',10,'1234-123','1234-123',10,100);
-- Input invalido (email xxx@xxx.xxx)
INSERT INTO Cliente (id_cliente,nome,nif,email,morada,morada_entrega,plafond,cod_postal_entrega,cod_postal,n_encomendas,valor_total_encomendas) VALUES (1,'Joao',123456789,'email@email.com','morada','morada',-1,'1234-123','1234-123',10,100);
-- Input invalido (plafond >= 0)
INSERT INTO Cliente (id_cliente,nome,nif,email,morada,morada_entrega,plafond,cod_postal_entrega,cod_postal,n_encomendas,valor_total_encomendas) VALUES (1,'Joao',123456789,'email@email.com','morada','morada',10,'1234-123','1234-123',-1,100);
-- Input invalido (n_encomendas >= 0)
INSERT INTO Cliente (id_cliente,nome,nif,email,morada,morada_entrega,plafond,cod_postal_entrega,cod_postal,n_encomendas,valor_total_encomendas) VALUES (1,'Joao',12345678910,'email@email.com','morada','morada',10,'1234-123','1234-123',10,-1);
-- Input invalido (valor_total_encomendas >=0)

INSERT INTO Encomenda (id_encomenda,id_cliente,data_vencimento_pagamento,data_registo,data_entrega,data_pagamento,morada_entrega,cod_postal_entrega) VALUES (1,1,'2020-01-01','2019-01-01','2020-01-01','2020-01-01','morada','1234-123');
-- Input invalido (data_vencimento_pagamento > data_registo)
INSERT INTO Encomenda (id_encomenda,id_cliente,data_vencimento_pagamento,data_registo,data_entrega,data_pagamento,morada_entrega,cod_postal_entrega) VALUES (1,1,'2020-01-01','2020-01-01','2019-01-01','2020-01-01','morada','1234-123');
-- Input invalido (data_entrega > data_registo)
INSERT INTO Encomenda (id_encomenda,id_cliente,data_vencimento_pagamento,data_registo,data_entrega,data_pagamento,morada_entrega,cod_postal_entrega) VALUES (1,1,'2020-01-01','2020-01-01','2020-01-01','2019-01-01','morada','1234-123');
-- Input invalido (data_pagamento > data_registo)

INSERT INTO ProdutoEncomenda (id_encomenda,id_produto,quantidade,preco_unitario,iva,designacao_produto) VALUES (1,1,-1,1,1,'produto');
-- Input invalido (quantidade > 0)
INSERT INTO ProdutoEncomenda (id_encomenda,id_produto,quantidade,preco_unitario,iva,designacao_produto) VALUES (1,1,1,-1,1,'produto');
-- Input invalido (preco_unitario > 0)
INSERT INTO ProdutoEncomenda (id_encomenda,id_produto,quantidade,preco_unitario,iva,designacao_produto) VALUES (1,1,1,1,-1,'produto');
-- Input invalido (iva > 0)

INSERT INTO Colheita (id_produto,data,quantidade,id_setor) VALUES (1,'2020-01-01',-1,1);
-- Input invalido (quantidade > 0)

-- Localidades (verificaçao de restriçoes)
INSERT INTO Localidade (cod_postal,localidade) VALUES ('1000-00','Lisboa');
-- FALHA (verificaçao de restriçoes)
INSERT INTO Localidade (cod_postal,localidade) VALUES ('1000-003','aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa');
INSERT INTO Localidade (cod_postal,localidade) VALUES (NULL,NULL);
-- FALHA (verificaçao de restriçoes)

-- Clientes (verificaçao de restriçoes)
INSERT INTO Cliente (id_cliente,nome,nif,email,morada,morada_entrega,plafond,cod_postal_entrega,cod_postal,n_encomendas,valor_total_encomendas) VALUES (NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
-- FALHA (verificaçao de restriçoes)
INSERT INTO Cliente (id_cliente,nome,nif,email,morada,morada_entrega,plafond,cod_postal_entrega,cod_postal,n_encomendas,valor_total_encomendas) VALUES (123456789,'nome',123456789,'email@email.com','morada','morada_entrega',123,'1234-123','1234-123',123,123);
-- FALHA (verificaçao de restriçoes)
INSERT INTO Cliente (id_cliente,nome,nif,email,morada,morada_entrega,plafond,cod_postal_entrega,cod_postal,n_encomendas,valor_total_encomendas) VALUES (12345678,'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa',123456789,'email@email.com','morada','morada_entrega',123,'1234-123','1234-123',123,123);
-- FALHA (verificaçao de restriçoes)
INSERT INTO Cliente (id_cliente,nome,nif,email,morada,morada_entrega,plafond,cod_postal_entrega,cod_postal,n_encomendas,valor_total_encomendas) VALUES (12345678,'nome',123456789,'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa','morada','morada_entrega',123,'1234-123','1234-123',123,123);
-- FALHA (verificaçao de restriçoes)
INSERT INTO Cliente (id_cliente,nome,nif,email,morada,morada_entrega,plafond,cod_postal_entrega,cod_postal,n_encomendas,valor_total_encomendas) VALUES (12345678,'nome',123456789,'email@email.com','aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa','morada_entrega',123,'1234-123','1234-123',123,123);
-- FALHA (verificaçao de restriçoes)
INSERT INTO Cliente (id_cliente,nome,nif,email,morada,morada_entrega,plafond,cod_postal_entrega,cod_postal,n_encomendas,valor_total_encomendas) VALUES (12345678,'nome',123456789,'email@email.com','morada','aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa',123,'1234-123','1234-123',123,123);
-- FALHA (verificaçao de restriçoes)
INSERT INTO Cliente (id_cliente,nome,nif,email,morada,morada_entrega,plafond,cod_postal_entrega,cod_postal,n_encomendas,valor_total_encomendas) VALUES (12345678,'nome',123456789,'email@email.com','morada','morada_entrega',123,'12-12','1234-123',123,123);
-- FALHA (verificaçao de restriçoes)
INSERT INTO Cliente (id_cliente,nome,nif,email,morada,morada_entrega,plafond,cod_postal_entrega,cod_postal,n_encomendas,valor_total_encomendas) VALUES (12345678,'nome',123456789,'email@email.com','morada','morada_entrega',123,'1234-123','12-12',123,123);
-- FALHA (verificaçao de restriçoes)
INSERT INTO Cliente (id_cliente,nome,nif,email,morada,morada_entrega,plafond,cod_postal_entrega,cod_postal,n_encomendas,valor_total_encomendas) VALUES (12345678,'nome',123456789,'email@email.com','morada','morada_entrega',123,'1234-123','1234-123',1234,123);
-- FALHA (verificaçao de restriçoes)
INSERT INTO Cliente (id_cliente,nome,nif,email,morada,morada_entrega,plafond,cod_postal_entrega,cod_postal,n_encomendas,valor_total_encomendas) VALUES (12345678,'nome',123456789,'email@email.com','morada','morada_entrega',123,'1234-123','1234-123',123,12345678910);
-- FALHA (verificaçao de restriçoes)

-- EscalaoIva (verificaçao de restriçoes)
INSERT INTO EscalaoIva (id_escalao_iva,valor) VALUES (NULL,NULL);
-- FALHA (verificaçao de restriçoes)
INSERT INTO EscalaoIva (id_escalao_iva,valor) VALUES (999,10);
-- FALHA (verificaçao de restriçoes)

-- Setor (verificaçao de restriçoes)
INSERT INTO Setor (id_setor,designacao,area) VALUES (NULL,NULL,NULL);
-- FALHA
INSERT INTO Setor (id_setor,designacao,area) VALUES (12345678901,'designacao',100);
-- FALHA
INSERT INTO Setor (id_setor,designacao,area) VALUES (123456789,'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa',100);
-- FALHA
INSERT INTO Setor (id_setor,designacao,area) VALUES (123456789,'designacao',1000000000);
-- FALHA

-- Produtos (verificaçao de restriçoes)
INSERT INTO Produto (id_produto,designacao,preco,id_escalao_iva) VALUES (NULL,NULL,NULL,NULL);
-- FALHA
INSERT INTO Produto (id_produto,designacao,preco,id_escalao_iva) VALUES (123456789,'designacao',10,13);
-- FALHA
INSERT INTO Produto (id_produto,designacao,preco,id_escalao_iva) VALUES (12345678,'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa',10,13);
-- FALHA
INSERT INTO Produto (id_produto,designacao,preco,id_escalao_iva) VALUES (12345678,'designacao',10,999);
-- FALHA

-- TipoCultura (verificaçao de restriçoes)
INSERT INTO TipoCultura (id_tipo_cultura,tipo_cultura) VALUES (NULL,NULL);
-- FALHA
INSERT INTO TipoCultura (id_tipo_cultura,tipo_cultura) VALUES (999,'cultura');
-- FALHA
INSERT INTO TipoCultura (id_tipo_cultura,tipo_cultura) VALUES (1,'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa');
-- FALHA

-- Cultura (verificaçao de restriçoes)
INSERT INTO Cultura (id_cultura,cultura,id_tipo_cultura) VALUES (NULL,NULL,NULL);
-- FALHA
INSERT INTO Cultura (id_cultura,cultura,id_tipo_cultura) VALUES (999,'Cultura',10);
-- FALHA
INSERT INTO Cultura (id_cultura,cultura,id_tipo_cultura) VALUES (1,'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa',10);
-- FALHA
INSERT INTO Cultura (id_cultura,cultura,id_tipo_cultura) VALUES (1,'Cultura',999);
-- FALHA

-- TipoFatorProducao (verificaçao de restrições)
INSERT INTO TipoFatorProducao (id_tipo_fator_producao,tipo_fator_producao) VALUES (NULL,NULL);
-- FALHA
INSERT INTO TipoFatorProducao (id_tipo_fator_producao,tipo_fator_producao) VALUES (999, 'tipo_fator_producao');
-- FALHA
INSERT INTO TipoFatorProducao (id_tipo_fator_producao,tipo_fator_producao) VALUES (1, 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa');
-- FALHA

-- Substancia (verificaçao de restriçoes)
INSERT INTO Substancia (id_substancia,substancia) VALUES (NULL,NULL);
-- FALHA
INSERT INTO Substancia (id_substancia,substancia) VALUES (999,'Substancia');
-- FALHA
INSERT INTO Substancia (id_substancia,substancia) VALUES (1,'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa');

-- TipoFormulacao (verificaçao de restriçoes)
INSERT INTO TipoFormulacao (id_tipo_formulacao,tipo_formulacao) VALUES (NULL,NULL);
-- FALHA
INSERT INTO TipoFormulacao (id_tipo_formulacao,tipo_formulacao) VALUES (999,'tipo_formulacao');
-- FALHA
INSERT INTO TipoFormulacao (id_tipo_formulacao,tipo_formulacao) VALUES (1,'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa');

-- FatorProducao (verificaçao de restriçoes)
INSERT INTO FatorProducao (id_fator_producao,id_tipo_fator_producao,nome,id_tipo_formulacao) VALUES (NULL,NULL,NULL,NULL);
-- FALHA (teste prova restricoes)
INSERT INTO FatorProducao (id_fator_producao,id_tipo_fator_producao,nome,id_tipo_formulacao) VALUES (999,999,'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa',999);
-- FALHA (teste prova restricoes)

-- FatorProducaoSubstancia (verificaçao de restriçoes)
INSERT INTO FatorProducaoSubstancia (id_fator_producao,id_substancia,percentagem) VALUES (NULL,NULL,NULL);
-- FALHA (teste prova restricoes)
INSERT INTO FatorProducaoSubstancia (id_fator_producao,id_substancia,percentagem) VALUES (999,999,9999);
-- FALHA (teste prova restricoes)

-- TipoTubagem (verificaçao de restriçoes)
INSERT INTO TipoTubagem (id_tipo_tubagem,tipo_tubagem) VALUES (NULL,NULL);
-- FALHA (teste prova restricoes)
INSERT INTO TipoTubagem (id_tipo_tubagem,tipo_tubagem) VALUES (999,'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa');
-- FALHA (teste prova restricoes)

-- TipoRega (verificaçao de restriçoes)
INSERT INTO TipoRega (id_tipo_rega,id_tipo_tubagem) VALUES (NULL,NULL);
-- FALHA (teste prova restricoes)
INSERT INTO TipoRega (id_tipo_rega,id_tipo_tubagem) VALUES (999,12345678910);
-- FALHA (teste prova restricoes)

-- PlanoRega (verificaçao de restriçoes)
INSERT INTO PlanoRega (id_setor,data_inicio,tempo,periodicidade,id_tipo_rega,data_fim) VALUES (NULL,NULL,NULL,NULL,NULL,NULL);
-- FALHA (teste prova restricoes)
INSERT INTO PlanoRega (id_setor,data_inicio,tempo,periodicidade,id_tipo_rega,data_fim) VALUES (12345678910,'20171-01-01',12345678910,'201712-01-01',999,'aa');
-- FALHA (teste prova restricoes)

-- TipoSensor (verificaçao de restriçoes)
INSERT INTO TipoSensor (id_tipo_sensor,tipo_sensor) VALUES (NULL,NULL);
-- FALHA (teste prova restricoes)
INSERT INTO TipoSensor (id_tipo_sensor,tipo_sensor) VALUES (999,'aaa');
-- FALHA (teste prova restricoes)

-- Sensor (verificaçao de restriçoes)
INSERT INTO Sensor (id_sensor,identificador,id_tipo_sensor,valor_referencia) VALUES (NULL,NULL,NULL,NULL);
-- FALHA (teste prova restricoes)
INSERT INTO Sensor (id_sensor,identificador,id_tipo_sensor,valor_referencia) VALUES (123456789,'aaaaaa',999,999);
-- FALHA (teste prova restricoes)


-- Encomenda (verificacao de restricoes)
INSERT INTO Encomenda (id_encomenda,id_cliente,data_vencimento_pagamento,data_registo,data_entrega,data_pagamento,morada_entrega,cod_postal_entrega) VALUES (NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
-- FALHA (teste prova restricoes)
INSERT INTO Encomenda (id_encomenda,id_cliente,data_vencimento_pagamento,data_registo,data_entrega,data_pagamento,morada_entrega,cod_postal_entrega) VALUES (12345678910,123456789,'2017a-01-01','2017a-01-01','2017-a1-01','aaa','aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa','aaaaaaaaa');
-- FALHA (teste prova restricoes)

-- ProdutoEncomenda (verificacao de restricoes)
INSERT INTO ProdutoEncomenda (id_encomenda,id_produto,quantidade,preco_unitario,iva,designacao_produto) VALUES (NULL,NULL,NULL,NULL,NULL,NULL);
-- FALHA (teste prova restricoes)
INSERT INTO ProdutoEncomenda (id_encomenda,id_produto,quantidade,preco_unitario,iva,designacao_produto) VALUES (12345678910,12345678910,99999,999,999,'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa');
-- FALHA (teste prova restricoes)

-- MedicaoSensor (verificacao de restricoes)
INSERT INTO MedicaoSensor (id_sensor,id_setor,medicao,data_hora) VALUES (NULL,NULL,NULL,NULL);
-- FALHA (teste prova restricoes)
INSERT INTO MedicaoSensor (id_sensor,id_setor,medicao,data_hora) VALUES (12345678910,12345678910,99999,'2017');
-- FALHA (teste prova restricoes)

-- Rega (verificacao de restricoes)
INSERT INTO Rega (id_setor,data,id_tipo_rega) VALUES (NULL,NULL,NULL);
-- FALHA (teste prova restricoes)
INSERT INTO Rega (id_setor,data,id_tipo_rega) VALUES (12345678910,'2017',12345678910);
-- FALHA (teste prova restricoes)

-- Colheita (verificacao de restricoes)
INSERT INTO Colheita (id_produto,data,quantidade,id_setor) VALUES (NULL,NULL,NULL,NULL);
-- FALHA (teste prova restricoes)
INSERT INTO Colheita (id_produto,data,quantidade,id_setor) VALUES (12345678910,'2017',9999999999,123456789101);
-- FALHA (teste prova restricoes)

-- Plantacao (verificacao de restricoes)
INSERT INTO Plantacao (id_setor,id_cultura,data_inicio) VALUES (NULL,NULL,NULL);
-- FALHA (teste prova restricoes)
INSERT INTO Plantacao (id_setor,id_cultura,data_inicio) VALUES (1234567891012,123,'2017');
-- FALHA (teste prova restricoes)

-- TipoEdificio (verificacao de restricoes)
INSERT INTO TipoEdificio (id_tipo_edificio,tipo_edificio) VALUES (NULL,NULL);
-- FALHA (teste prova restricoes)
INSERT INTO TipoEdificio (id_tipo_edificio,tipo_edificio) VALUES (999,'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa');
-- FALHA (teste prova restricoes)

-- Edificio (verificacao de restricoes)
INSERT INTO Edificio (id_edificio,id_tipo_edificio) VALUES (NULL,NULL);
-- FALHA (teste prova restricoes)
INSERT INTO Edificio (id_edificio,id_tipo_edificio) VALUES (12345678910,999);
-- FALHA (teste prova restricoes)

-- TipoAplicacaoFatorProducao (verificacao de restricoes)
INSERT INTO TipoAplicacaoFatorProducao (id_tipo_aplicacao_fator_producao,tipo_aplicacao_fator_producao) VALUES (NULL,NULL);
-- FALHA (teste prova restricoes)
INSERT INTO TipoAplicacaoFatorProducao (id_tipo_aplicacao_fator_producao,tipo_aplicacao_fator_producao) VALUES (9999,'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa');
-- FALHA (teste prova restricoes)

-- AplicacaoFatorProducao (verificacao de restricoes)
INSERT INTO AplicacaoFatorProducao (id_setor,id_fator_producao,data,id_tipo_aplicacao_fator_producao) VALUES (NULL,NULL,NULL,NULL);
-- FALHA (teste prova restricoes)
INSERT INTO AplicacaoFatorProducao (id_setor,id_fator_producao,data,id_tipo_aplicacao_fator_producao) VALUES (12345678910,12345678910,'2017',9999);
-- FALHA (teste prova restricoes)

-- ProdutoCultura (verificacao de restricoes)
INSERT INTO ProdutoCultura (id_cultura,id_produto) VALUES (NULL,NULL);
-- FALHA (teste prova restricoes)
INSERT INTO ProdutoCultura (id_cultura,id_produto) VALUES (12345678910,12345678910);
-- FALHA (teste prova restricoes)

