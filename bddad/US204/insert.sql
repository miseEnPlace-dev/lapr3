-- Localidades
INSERT INTO Localidade (cod_postal,localidade) VALUES ('1234-567','Porto');
INSERT INTO Localidade (cod_postal,localidade) VALUES ('1234-566','Porto');
INSERT INTO Localidade (cod_postal,localidade) VALUES ('1234-565','Porto');

-- Clientes
INSERT INTO Cliente (id_cliente,nome,nif,email,morada,morada_entrega,plafond,cod_postal_entrega,cod_postal) VALUES (1,'Joao',123456789,'email@gmail.com','Rua do Joao','Rua do Joao','1000','1234-567','1234-567');
INSERT INTO Cliente (id_cliente,nome,nif,email,morada,morada_entrega,plafond,cod_postal_entrega,cod_postal) VALUES (2,'Maria',987654321,'maria@gmail.com','Rua da Maria','Rua da Maria','1000','1234-566','1234-566');
INSERT INTO Cliente (id_cliente,nome,nif,email,morada,morada_entrega,plafond,cod_postal_entrega,cod_postal) VALUES (3,'Jose',111111111,'jose@gmail.com','Rua do Jose','Rua do Jose','1000','1234-565','1234-565');

-- Setores
INSERT INTO Setor (id_setor,designacao,area) VALUES (1,'Setor 1',200);
INSERT INTO Setor (id_setor,designacao,area) VALUES (2,'Setor 2',100);
INSERT INTO Setor (id_setor,designacao,area) VALUES (3,'Setor 3',300);

-- EscalaoIva
INSERT INTO EscalaoIva (id_escalao_iva,valor) VALUES (1,23);
INSERT INTO EscalaoIva (id_escalao_iva,valor) VALUES (2,13);
INSERT INTO EscalaoIva (id_escalao_iva,valor) VALUES (3,6);

-- Produtos
INSERT INTO Produto (id_produto,designacao,preco,id_escalao_iva) VALUES (1,'Maça',2,2);
INSERT INTO Produto (id_produto,designacao,preco,id_escalao_iva) VALUES (2,'Pera',2,2);
INSERT INTO Produto (id_produto,designacao,preco,id_escalao_iva) VALUES (3,'Laranja',2,2);
INSERT INTO Produto (id_produto,designacao,preco,id_escalao_iva) VALUES (4,'Cerejeira',10,1);

-- Encomendas
INSERT INTO Encomenda (id_encomenda,id_cliente,data_vencimento_pagamento,data_registo,data_entrega,data_pagamento,morada_entrega,cod_postal_entrega) VALUES (1,1,'01-Feb-2022','01-Jan-2022',NULL,NULL,'Rua do Joao','1234-567');
INSERT INTO Encomenda (id_encomenda,id_cliente,data_vencimento_pagamento,data_registo,data_entrega,data_pagamento,morada_entrega,cod_postal_entrega) VALUES (2,2,'12-Feb-2022','02-Feb-2022',NULL,NULL,'Rua da Maria','1234-566');
INSERT INTO Encomenda (id_encomenda,id_cliente,data_vencimento_pagamento,data_registo,data_entrega,data_pagamento,morada_entrega,cod_postal_entrega) VALUES (3,3,'13-Feb-2022','03-Feb-2022',NULL,NULL,'Rua do Jose','1234-565');
INSERT INTO Encomenda (id_encomenda,id_cliente,data_vencimento_pagamento,data_registo,data_entrega,data_pagamento,morada_entrega,cod_postal_entrega) VALUES (4,1,'25-Apr-2022','15-Apr-2022',NULL,NULL,'Rua do Joao','1234-567');


-- ProdutoEncomenda
INSERT INTO ProdutoEncomenda (id_encomenda,id_produto,quantidade,preco_unitario,iva,designacao_produto) VALUES (1,1,500,2,13,'Maça');
INSERT INTO ProdutoEncomenda (id_encomenda,id_produto,quantidade,preco_unitario,iva,designacao_produto) VALUES (2,2,1000,2,13,'Pera');
INSERT INTO ProdutoEncomenda (id_encomenda,id_produto,quantidade,preco_unitario,iva,designacao_produto) VALUES (3,3,2000,2,13,'Laranja');
INSERT INTO ProdutoEncomenda (id_encomenda,id_produto,quantidade,preco_unitario,iva,designacao_produto) VALUES (4,4,3000,10,23,'Cerejeira');

-- TipoCulturas
INSERT INTO TipoCultura (id_tipo_cultura,tipo_cultura) VALUES (1,'Temporária');
INSERT INTO TipoCultura (id_tipo_cultura,tipo_cultura) VALUES (2,'Permanentemente');

-- Culturas
INSERT INTO Cultura (id_cultura,cultura,id_tipo_cultura) VALUES (1,'Macieira',1);
INSERT INTO Cultura (id_cultura,cultura,id_tipo_cultura) VALUES (2,'Pereira',1);
INSERT INTO Cultura (id_cultura,cultura,id_tipo_cultura) VALUES (3,'Laranjeira',1);
INSERT INTO Cultura (id_cultura,cultura,id_tipo_cultura) VALUES (4,'Cerejeira',1);

-- ProdutoCultura
INSERT INTO ProdutoCultura (id_produto,id_cultura) VALUES (1,1);
INSERT INTO ProdutoCultura (id_produto,id_cultura) VALUES (2,2);

-- TipoTubagem
INSERT INTO TipoTubagem (id_tipo_tubagem,tipo_tubagem) VALUES (1,'Dispersão');
INSERT INTO TipoTubagem (id_tipo_tubagem,tipo_tubagem) VALUES (2,'Irrigação');
INSERT INTO TipoTubagem (id_tipo_tubagem,tipo_tubagem) VALUES (3,'Drenagem');
INSERT INTO TipoTubagem (id_tipo_tubagem,tipo_tubagem) VALUES (4,'Gravidade');

-- TipoRega
INSERT INTO TipoRega (id_tipo_rega,id_tipo_tubagem) VALUES (1,1);
INSERT INTO TipoRega (id_tipo_rega,id_tipo_tubagem) VALUES (2,2);
INSERT INTO TipoRega (id_tipo_rega,id_tipo_tubagem) VALUES (3,3);
INSERT INTO TipoRega (id_tipo_rega,id_tipo_tubagem) VALUES (4,4);

-- Rega
INSERT INTO Rega (id_setor,data,id_tipo_rega) VALUES (1,CURRENT_DATE,1);
INSERT INTO Rega (id_setor,data,id_tipo_rega) VALUES (2,CURRENT_DATE,2);
INSERT INTO Rega (id_setor,data,id_tipo_rega) VALUES (3,CURRENT_DATE,3);
INSERT INTO Rega (id_setor,data,id_tipo_rega) VALUES (1,CURRENT_DATE,4);

-- PlanoRega
INSERT INTO PlanoRega (id_setor,data_inicio,tempo,periodicidade,id_tipo_rega,data_fim) VALUES (1,CURRENT_DATE,1,1,1,Null);

-- TipoEdificio
INSERT INTO TipoEdificio (id_tipo_edificio,tipo_edificio) VALUES (1,'Armazem');
INSERT INTO TipoEdificio (id_tipo_edificio,tipo_edificio) VALUES (2,'Silo');
INSERT INTO TipoEdificio (id_tipo_edificio,tipo_edificio) VALUES (3,'Casa das máquinas');
INSERT INTO TipoEdificio (id_tipo_edificio,tipo_edificio) VALUES (4,'Sistema de rega');

-- Edificio
INSERT INTO Edificio (id_edificio,id_tipo_edificio) VALUES (1,1);
INSERT INTO Edificio (id_edificio,id_tipo_edificio) VALUES (2,2);

-- Substancia
INSERT INTO Substancia (id_substancia,substancia) VALUES (1,'Azoto orgânico');
INSERT INTO Substancia (id_substancia,substancia) VALUES (2,'Pentóxido de fósforo');
INSERT INTO Substancia (id_substancia,substancia) VALUES (3,'Oxido de Potássio');
INSERT INTO Substancia (id_substancia,substancia) VALUES (4,'Oxido de Cálcio');
INSERT INTO Substancia (id_substancia,substancia) VALUES (5,'Oxido de Magnésio');

--TipoFatorProducao
INSERT INTO TipoFatorProducao (id_tipo_fator_producao,tipo_fator_producao) VALUES (1,'Fertilizante');
INSERT INTO TipoFatorProducao (id_tipo_fator_producao,tipo_fator_producao) VALUES (2,'Adubo');

-- TipoFormulacao
INSERT INTO TipoFormulacao (id_tipo_formulacao,tipo_formulacao) VALUES (1,'Granulado');
INSERT INTO TipoFormulacao (id_tipo_formulacao,tipo_formulacao) VALUES (2,'Líquido');
INSERT INTO TipoFormulacao (id_tipo_formulacao,tipo_formulacao) VALUES (3,'Pó');

-- FatorProducao
INSERT INTO FatorProducao (id_fator_producao,id_tipo_fator_producao,nome,id_tipo_formulacao) VALUES (1,1,'Fertilizante 1',1);
INSERT INTO FatorProducao (id_fator_producao,id_tipo_fator_producao,nome,id_tipo_formulacao) VALUES (2,2,'Adubo 1',2);

-- FatorProducaoSubstancia
INSERT INTO FatorProducaoSubstancia (id_fator_producao,id_substancia,percentagem) VALUES (1,1,60);
INSERT INTO FatorProducaoSubstancia (id_fator_producao,id_substancia,percentagem) VALUES (1,2,20);

-- TipoSensor
INSERT INTO TipoSensor (id_tipo_sensor,tipo_sensor) VALUES (1,'TS');
INSERT INTO TipoSensor (id_tipo_sensor,tipo_sensor) VALUES (2,'HS');
INSERT INTO TipoSensor (id_tipo_sensor,tipo_sensor) VALUES (3,'VS');
INSERT INTO TipoSensor (id_tipo_sensor,tipo_sensor) VALUES (4,'DS');
INSERT INTO TipoSensor (id_tipo_sensor,tipo_sensor) VALUES (5,'HA');
INSERT INTO TipoSensor (id_tipo_sensor,tipo_sensor) VALUES (6,'PL');

-- Sensor
INSERT INTO Sensor (id_sensor,identificador,id_tipo_sensor,valor_referencia) VALUES (1,'TS',1,10);
INSERT INTO Sensor (id_sensor,identificador,id_tipo_sensor,valor_referencia) VALUES (2,'HS',2,10);
INSERT INTO Sensor (id_sensor,identificador,id_tipo_sensor,valor_referencia) VALUES (3,'VS',3,10);
INSERT INTO Sensor (id_sensor,identificador,id_tipo_sensor,valor_referencia) VALUES (4,'DS',4,10);
INSERT INTO Sensor (id_sensor,identificador,id_tipo_sensor,valor_referencia) VALUES (5,'HA',5,10);
INSERT INTO Sensor (id_sensor,identificador,id_tipo_sensor,valor_referencia) VALUES (6,'PL',6,10);

-- MedicaoSensor
INSERT INTO MedicaoSensor (id_sensor,id_setor,medicao,data_hora) VALUES (1,1,20,CURRENT_DATE);
INSERT INTO MedicaoSensor (id_sensor,id_setor,medicao,data_hora) VALUES (2,1,60,CURRENT_DATE);
INSERT INTO MedicaoSensor (id_sensor,id_setor,medicao,data_hora) VALUES (3,1,10,CURRENT_DATE);
INSERT INTO MedicaoSensor (id_sensor,id_setor,medicao,data_hora) VALUES (4,1,10,CURRENT_DATE);
INSERT INTO MedicaoSensor (id_sensor,id_setor,medicao,data_hora) VALUES (5,1,80,CURRENT_DATE);
INSERT INTO MedicaoSensor (id_sensor,id_setor,medicao,data_hora) VALUES (6,1,5,CURRENT_DATE);

-- Plantacao
INSERT INTO Plantacao (id_setor,id_cultura,data_inicio) VALUES (1,1,CURRENT_DATE);
INSERT INTO Plantacao (id_setor,id_cultura,data_inicio) VALUES (2,2,CURRENT_DATE);
INSERT INTO Plantacao (id_setor,id_cultura,data_inicio) VALUES (3,3,CURRENT_DATE);

-- Colheita
INSERT INTO Colheita (id_produto,data,quantidade,id_setor) VALUES (1,CURRENT_DATE,10,1);
INSERT INTO Colheita (id_produto,data,quantidade,id_setor) VALUES (2,CURRENT_DATE,10,2);
INSERT INTO Colheita (id_produto,data,quantidade,id_setor) VALUES (3,CURRENT_DATE,10,3);

-- TipoAplicacaoFatorProducao
INSERT INTO TipoAplicacaoFatorProducao (id_tipo_aplicacao_fator_producao,tipo_aplicacao_fator_producao) VALUES (1,'Foliar');
INSERT INTO TipoAplicacaoFatorProducao (id_tipo_aplicacao_fator_producao,tipo_aplicacao_fator_producao) VALUES (2,'Solo');

-- AplicacaoFatorProducao
INSERT INTO AplicacaoFatorProducao (id_setor,id_fator_producao,data,id_tipo_aplicacao_fator_producao) VALUES (1,1,CURRENT_DATE,1);
INSERT INTO AplicacaoFatorProducao (id_setor,id_fator_producao,data,id_tipo_aplicacao_fator_producao) VALUES (2,2,CURRENT_DATE,2);

-- Visualisacao de dados
DECLARE
    numero_dados NUMBER;
BEGIN
    DBMS_OUTPUT.PUT_LINE('Quantidade de registos em cada tabela:');
    FOR I IN (SELECT TABLE_NAME FROM USER_TABLES)
        LOOP
            EXECUTE IMMEDIATE 'SELECT count(*) FROM ' || i.TABLE_NAME INTO numero_dados;
            DBMS_OUTPUT.PUT_LINE('Tabela ' || i.TABLE_NAME || ': ' || numero_dados || ' registos');
        END LOOP;
END;
