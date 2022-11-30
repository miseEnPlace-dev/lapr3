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
INSERT INTO Encomenda (id_cliente,data_vencimento_pagamento,data_registo,data_entrega,data_pagamento,morada_entrega,cod_postal_entrega) VALUES (1,CURRENT_DATE,CURRENT_DATE,NULL,NULL,'Rua do Joao','1234-567');
INSERT INTO Encomenda (id_cliente,data_vencimento_pagamento,data_registo,data_entrega,data_pagamento,morada_entrega,cod_postal_entrega) VALUES (2,CURRENT_DATE,CURRENT_DATE,NULL,NULL,'Rua da Maria','1234-566');
INSERT INTO Encomenda (id_cliente,data_vencimento_pagamento,data_registo,data_entrega,data_pagamento,morada_entrega,cod_postal_entrega) VALUES (3,CURRENT_DATE,CURRENT_DATE,NULL,NULL,'Rua do Jose','1234-565');
INSERT INTO Encomenda (id_cliente,data_vencimento_pagamento,data_registo,data_entrega,data_pagamento,morada_entrega,cod_postal_entrega) VALUES (1,CURRENT_DATE,CURRENT_DATE,NULL,NULL,'Rua do Joao','1234-567');

-- ProdutoEncomenda
INSERT INTO ProdutoEncomenda (id_encomenda,id_produto,quantidade,preco_unitario,iva,designacao_produto) VALUES (1,1,500,2,13,'Maça');
INSERT INTO ProdutoEncomenda (id_encomenda,id_produto,quantidade,preco_unitario,iva,designacao_produto) VALUES (2,2,1000,2,13,'Pera');
INSERT INTO ProdutoEncomenda (id_encomenda,id_produto,quantidade,preco_unitario,iva,designacao_produto) VALUES (3,3,2000,2,13,'Laranja');
INSERT INTO ProdutoEncomenda (id_encomenda,id_produto,quantidade,preco_unitario,iva,designacao_produto) VALUES (4,4,3000,10,23,'Cerejeira');

-- TipoCulturas
INSERT INTO TipoCultura (tipo_cultura) VALUES ('Temporária');
INSERT INTO TipoCultura (tipo_cultura) VALUES ('Permanentemente');

-- Culturas
INSERT INTO Cultura (cultura,id_tipo_cultura,id_produto) VALUES ('Macieira',1,1);
INSERT INTO Cultura (cultura,id_tipo_cultura,id_produto) VALUES ('Pereira',1,2);
INSERT INTO Cultura (cultura,id_tipo_cultura,id_produto) VALUES ('Laranjeira',1,1);
INSERT INTO Cultura (cultura,id_tipo_cultura,id_produto) VALUES ('Cerejeira',1,1);

-- TipoTubagem
INSERT INTO TipoTubagem (tipo_tubagem) VALUES ('Dispersão');
INSERT INTO TipoTubagem (tipo_tubagem) VALUES ('Irrigação');
INSERT INTO TipoTubagem (tipo_tubagem) VALUES ('Drenagem');
INSERT INTO TipoTubagem (tipo_tubagem) VALUES ('Gravidade');

-- TipoRega
INSERT INTO TipoRega (id_tipo_tubagem) VALUES (1);
INSERT INTO TipoRega (id_tipo_tubagem) VALUES (2);
INSERT INTO TipoRega (id_tipo_tubagem) VALUES (3);
INSERT INTO TipoRega (id_tipo_tubagem) VALUES (4);

-- Rega
INSERT INTO Rega (id_setor,data,id_tipo_rega) VALUES (1,'10-Jan-2022',1);
INSERT INTO Rega (id_setor,data,id_tipo_rega) VALUES (2,'11-Jan-2022',2);
INSERT INTO Rega (id_setor,data,id_tipo_rega) VALUES (3,'12-Jan-2022',3);
INSERT INTO Rega (id_setor,data,id_tipo_rega) VALUES (1,'13-Jan-2022',4);

-- PlanoRega
INSERT INTO PlanoRega (id_setor,data_inicio,tempo,periodicidade,id_tipo_rega,data_fim) VALUES (1,'10-Jan-2022',1,1,1,Null);

-- TipoEdificio
INSERT INTO TipoEdificio (tipo_edificio) VALUES ('Armazem');
INSERT INTO TipoEdificio (tipo_edificio) VALUES ('Silo');

-- Edificio
INSERT INTO Edificio (id_tipo_edificio) VALUES (1);
INSERT INTO Edificio (id_tipo_edificio) VALUES (2);

-- Substancia
INSERT INTO Substancia (substancia) VALUES ('Azoto orgânico');
INSERT INTO Substancia (substancia) VALUES ('Pentóxido de fósforo');
INSERT INTO Substancia (substancia) VALUES ('Oxido de Potássio');
INSERT INTO Substancia (substancia) VALUES ('Oxido de Cálcio');
INSERT INTO Substancia (substancia) VALUES ('Oxido de Magnésio');

--TipoFatorProducao
INSERT INTO TipoFatorProducao (tipo_fator_producao) VALUES ('Fertilizante');
INSERT INTO TipoFatorProducao (tipo_fator_producao) VALUES ('Adubo');

-- TipoFormulacao
INSERT INTO TipoFormulacao (tipo_formulacao) VALUES ('Granulado');
INSERT INTO TipoFormulacao (tipo_formulacao) VALUES ('Líquido');
INSERT INTO TipoFormulacao (tipo_formulacao) VALUES ('Pó');

-- FatorProducao
INSERT INTO FatorProducao (id_tipo_fator_producao,nome,id_tipo_formulacao) VALUES (1,'Fertilizante 1',1);
INSERT INTO FatorProducao (id_tipo_fator_producao,nome,id_tipo_formulacao) VALUES (2,'Adubo 1',2);

-- FatorProducaoSubstancia
INSERT INTO FatorProducaoSubstancia (id_fator_producao,id_substancia,percentagem) VALUES (1,1,60);
INSERT INTO FatorProducaoSubstancia (id_fator_producao,id_substancia,percentagem) VALUES (1,2,20);

-- TipoSensor
INSERT INTO TipoSensor (tipo_sensor) VALUES ('TS');
INSERT INTO TipoSensor (tipo_sensor) VALUES ('HS');
INSERT INTO TipoSensor (tipo_sensor) VALUES ('VS');
INSERT INTO TipoSensor (tipo_sensor) VALUES ('DS');
INSERT INTO TipoSensor (tipo_sensor) VALUES ('HA');
INSERT INTO TipoSensor (tipo_sensor) VALUES ('PL');

-- Sensor
INSERT INTO Sensor (identificador,id_tipo_sensor,valor_referencia) VALUES ('TS',1,10);
INSERT INTO Sensor (identificador,id_tipo_sensor,valor_referencia) VALUES ('HS',2,10);
INSERT INTO Sensor (identificador,id_tipo_sensor,valor_referencia) VALUES ('VS',3,10);
INSERT INTO Sensor (identificador,id_tipo_sensor,valor_referencia) VALUES ('DS',4,10);
INSERT INTO Sensor (identificador,id_tipo_sensor,valor_referencia) VALUES ('HA',5,10);
INSERT INTO Sensor (identificador,id_tipo_sensor,valor_referencia) VALUES ('PL',6,10);

-- MedicaoSensor
INSERT INTO MedicaoSensor (id_sensor,id_setor,medicao,data_hora) VALUES (1,1,20,'10-Jan-2022');
INSERT INTO MedicaoSensor (id_sensor,id_setor,medicao,data_hora) VALUES (2,1,60,'10-Jan-2022');
INSERT INTO MedicaoSensor (id_sensor,id_setor,medicao,data_hora) VALUES (3,1,10,'10-Jan-2022');
INSERT INTO MedicaoSensor (id_sensor,id_setor,medicao,data_hora) VALUES (4,1,10,'10-Jan-2022');
INSERT INTO MedicaoSensor (id_sensor,id_setor,medicao,data_hora) VALUES (5,1,80,'10-Jan-2022');
INSERT INTO MedicaoSensor (id_sensor,id_setor,medicao,data_hora) VALUES (6,1,5,'10-Jan-2022');

-- Plantacao
INSERT INTO Plantacao (id_setor,id_cultura,data_inicio) VALUES (1,1,'10-Jan-2022');
INSERT INTO Plantacao (id_setor,id_cultura,data_inicio) VALUES (2,2,'10-Jan-2022');
INSERT INTO Plantacao (id_setor,id_cultura,data_inicio) VALUES (3,3,'10-Jan-2022');

-- Colheita
INSERT INTO Colheita (id_produto,data,quantidade,id_setor) VALUES (1,'10-Jan-2022',10,1);
INSERT INTO Colheita (id_produto,data,quantidade,id_setor) VALUES (2,'10-Jan-2022',10,2);
INSERT INTO Colheita (id_produto,data,quantidade,id_setor) VALUES (3,'10-Jan-2022',10,3);

-- TipoAplicacaoFatorProducao
INSERT INTO TipoAplicacaoFatorProducao (tipo_aplicacao_fator_producao) VALUES ('Foliar');
INSERT INTO TipoAplicacaoFatorProducao (tipo_aplicacao_fator_producao) VALUES ('Solo');

-- AplicacaoFatorProducao
INSERT INTO AplicacaoFatorProducao (id_setor,id_fator_producao,data,id_tipo_aplicacao_fator_producao) VALUES (1,1,'10-Jan-2022',1);
INSERT INTO AplicacaoFatorProducao (id_setor,id_fator_producao,data,id_tipo_aplicacao_fator_producao) VALUES (2,2,'10-Jan-2022',2);

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
