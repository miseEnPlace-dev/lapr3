-- Delete content of tables
DELETE FROM Setor;
DELETE FROM Cultura;
DELETE FROM TipoCultura;
DELETE FROM FatorProducao;
DELETE FROM TipoFatorProducao;
DELETE FROM Substancia;
DELETE FROM FatorProducaoSubstancia;
DELETE FROM TipoFormulacao;
DELETE FROM PlanoRega;
DELETE FROM TipoRega;
DELETE FROM TipoTubagem;
DELETE FROM Sensor;
DELETE FROM TipoSensor;
DELETE FROM Cliente;
DELETE FROM ProdutoEncomenda;
DELETE FROM Produto;
DELETE FROM Localidade;
DELETE FROM EscalaoIva;
DELETE FROM MedicaoSensor;
DELETE FROM Operacao;
DELETE FROM Rega;
DELETE FROM Colheita;
DELETE FROM Encomenda;
DELETE FROM Plantacao;
DELETE FROM Edificio;
DELETE FROM TipoEdificio;
DELETE FROM Aplicacao;
DELETE FROM TipoAplicacao;
DELETE FROM ProdutoCultura;
DELETE FROM Fornecedor;
DELETE FROM CategoriaSubstancia;
DELETE FROM Logs;
DELETE FROM RestricaoAplicacao;
DELETE FROM FatorProducaoAplicacao;
DELETE FROM TipoAlteracao;
DELETE FROM InputSensor;
DELETE FROM InputHub;
DELETE FROM Hub;
DELETE FROM LeituraInputSensor;
DELETE FROM ErroLeituraInputSensor;

-- Localidades
INSERT INTO Localidade (cod_postal,localidade) VALUES ('1234-567','Porto');
INSERT INTO Localidade (cod_postal,localidade) VALUES ('1234-566','Porto');
INSERT INTO Localidade (cod_postal,localidade) VALUES ('1234-565','Porto');

-- Hub
INSERT INTO Hub (id_hub,codigo_hub,designacao,latitude,longitude) VALUES (1,'HUB1','Hub 1',1,1);
INSERT INTO Hub (id_hub,codigo_hub,designacao,latitude,longitude) VALUES (2,'HUB2','Hub 2',2,2);

-- Clientes
INSERT INTO Cliente (id_cliente,nome,nif,email,morada,id_hub_entrega,plafond,cod_postal) VALUES (1,'Joao',123456789,'email@gmail.com','Rua do Joao',1,'1000','1234-567');
INSERT INTO Cliente (id_cliente,nome,nif,email,morada,id_hub_entrega,plafond,cod_postal) VALUES (2,'Maria',987654321,'maria@gmail.com','Rua da Maria',1,'1000','1234-566');
INSERT INTO Cliente (id_cliente,nome,nif,email,morada,id_hub_entrega,plafond,cod_postal) VALUES (3,'Jose',111111111,'jose@gmail.com','Rua do Jose',2,'1000','1234-565');

-- Setores
INSERT INTO Setor (id_setor,designacao,area) VALUES (1,'Setor 1',200);
INSERT INTO Setor (id_setor,designacao,area) VALUES (2,'Setor 2',100);
INSERT INTO Setor (id_setor,designacao,area) VALUES (3,'Setor 3',300);
INSERT INTO Setor (id_setor,designacao,area) VALUES (4,'Setor 4',400);

-- EscalaoIva
INSERT INTO EscalaoIva (id_escalao_iva,valor) VALUES (1,23);
INSERT INTO EscalaoIva (id_escalao_iva,valor) VALUES (2,13);
INSERT INTO EscalaoIva (id_escalao_iva,valor) VALUES (3,6);

-- Produtos
INSERT INTO Produto (id_produto,designacao,preco,id_escalao_iva) VALUES (1,'Maça',2,2);
INSERT INTO Produto (id_produto,designacao,preco,id_escalao_iva) VALUES (2,'Pera',2,2);
INSERT INTO Produto (id_produto,designacao,preco,id_escalao_iva) VALUES (3,'Laranja',2,2);
INSERT INTO Produto (id_produto,designacao,preco,id_escalao_iva) VALUES (4,'Cerejeira',10,1);
INSERT INTO Produto (id_produto,designacao,preco,id_escalao_iva) VALUES (5,'Amora',10,1);

-- Encomendas
INSERT INTO Encomenda (id_encomenda,id_cliente,data_vencimento_pagamento,data_registo,data_entrega,data_pagamento,id_hub_entrega) VALUES (1,1,'01-Feb-2022','01-Jan-2022',NULL,NULL,1);
INSERT INTO Encomenda (id_encomenda,id_cliente,data_vencimento_pagamento,data_registo,data_entrega,data_pagamento,id_hub_entrega) VALUES (2,2,'12-Feb-2022','02-Feb-2022',NULL,NULL,1);
INSERT INTO Encomenda (id_encomenda,id_cliente,data_vencimento_pagamento,data_registo,data_entrega,data_pagamento,id_hub_entrega) VALUES (3,3,'13-Feb-2022','03-Feb-2022',NULL,NULL,2);
INSERT INTO Encomenda (id_encomenda,id_cliente,data_vencimento_pagamento,data_registo,data_entrega,data_pagamento,id_hub_entrega) VALUES (4,1,'25-Apr-2022','15-Apr-2022',NULL,NULL,2);


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
INSERT INTO Cultura (id_cultura,cultura,id_tipo_cultura) VALUES (4,'Cerejeira',2);
INSERT INTO Cultura (id_cultura,cultura,id_tipo_cultura) VALUES (5,'Amora',2);

-- ProdutoCultura
INSERT INTO ProdutoCultura (id_produto,id_cultura) VALUES (1,1);
INSERT INTO ProdutoCultura (id_produto,id_cultura) VALUES (2,2);

-- TipoTubagem
INSERT INTO TipoTubagem (id_tipo_tubagem,tipo_tubagem) VALUES (1,'Dispersão');
INSERT INTO TipoTubagem (id_tipo_tubagem,tipo_tubagem) VALUES (2,'Irrigação');
INSERT INTO TipoTubagem (id_tipo_tubagem,tipo_tubagem) VALUES (3,'Drenagem');
INSERT INTO TipoTubagem (id_tipo_tubagem,tipo_tubagem) VALUES (4,'Gravidade');

-- Operacao
INSERT INTO Operacao (id_operacao,data_prevista_operacao,data_operacao) VALUES (1,'01-Feb-2021','01-Feb-2021');
INSERT INTO Operacao (id_operacao,data_prevista_operacao,data_operacao) VALUES (2,'02-Feb-2021','02-Feb-2021');
INSERT INTO Operacao (id_operacao,data_prevista_operacao,data_operacao) VALUES (3,'03-Feb-2021',NULL);
INSERT INTO Operacao (id_operacao,data_prevista_operacao,data_operacao) VALUES (4,'04-Feb-2021',NULL);
INSERT INTO Operacao (id_operacao,data_prevista_operacao,data_operacao) VALUES (5,'05-Feb-2021',NULL);


-- TipoRega
INSERT INTO TipoRega (id_tipo_rega,id_tipo_tubagem) VALUES (1,1);
INSERT INTO TipoRega (id_tipo_rega,id_tipo_tubagem) VALUES (2,2);
INSERT INTO TipoRega (id_tipo_rega,id_tipo_tubagem) VALUES (3,3);
INSERT INTO TipoRega (id_tipo_rega,id_tipo_tubagem) VALUES (4,4);

-- Rega
INSERT INTO Rega (id_operacao,id_setor,id_tipo_rega) VALUES (1,1,1);
INSERT INTO Rega (id_operacao,id_setor,id_tipo_rega) VALUES (2,2,2);
INSERT INTO Rega (id_operacao,id_setor,id_tipo_rega) VALUES (3,3,3);
INSERT INTO Rega (id_operacao,id_setor,id_tipo_rega) VALUES (4,4,4);

-- PlanoRega
INSERT INTO PlanoRega (id_setor,data_inicio,tempo,periodicidade,id_tipo_rega,data_fim) VALUES (1,'10-Jan-2022',1,1,1,Null);

-- TipoEdificio
INSERT INTO TipoEdificio (id_tipo_edificio,tipo_edificio) VALUES (1,'Armazem');
INSERT INTO TipoEdificio (id_tipo_edificio,tipo_edificio) VALUES (2,'Silo');
INSERT INTO TipoEdificio (id_tipo_edificio,tipo_edificio) VALUES (3,'Casa das máquinas');
INSERT INTO TipoEdificio (id_tipo_edificio,tipo_edificio) VALUES (4,'Sistema de rega');

-- Edificio
INSERT INTO Edificio (id_edificio,id_tipo_edificio) VALUES (1,1);
INSERT INTO Edificio (id_edificio,id_tipo_edificio) VALUES (2,2);

-- Fornecedor
INSERT INTO Fornecedor (id_fornecedor,fornecedor) VALUES (1,'Alberto');
INSERT INTO Fornecedor (id_fornecedor,fornecedor) VALUES (2,'Joaquim');

-- CategoriaSubstancia
INSERT INTO CategoriaSubstancia (id_categoria_substancia,categoria_substancia) VALUES (1,'Materia Organica 1');
INSERT INTO CategoriaSubstancia (id_categoria_substancia,categoria_substancia) VALUES (2,'Materia Organica 2');
INSERT INTO CategoriaSubstancia (id_categoria_substancia,categoria_substancia) VALUES (3,'Materia Organica 3');
INSERT INTO CategoriaSubstancia (id_categoria_substancia,categoria_substancia) VALUES (4,'Materia Organica 4');
INSERT INTO CategoriaSubstancia (id_categoria_substancia,categoria_substancia) VALUES (5,'Materia Organica 5');

-- Substancia
INSERT INTO Substancia (id_substancia,substancia,id_fornecedor,id_categoria_substancia) VALUES (1,'Azoto orgânico',1,1);
INSERT INTO Substancia (id_substancia,substancia,id_fornecedor,id_categoria_substancia) VALUES (2,'Pentóxido de fósforo',1,2);
INSERT INTO Substancia (id_substancia,substancia,id_fornecedor,id_categoria_substancia) VALUES (3,'Oxido de Potássio',1,3);
INSERT INTO Substancia (id_substancia,substancia,id_fornecedor,id_categoria_substancia) VALUES (4,'Oxido de Cálcio',1,4);
INSERT INTO Substancia (id_substancia,substancia,id_fornecedor,id_categoria_substancia) VALUES (5,'Oxido de Magnésio',1,5);

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
INSERT INTO FatorProducaoSubstancia (id_fator_producao,id_substancia,quantidade,unidade) VALUES (1,1,60,'kg');
INSERT INTO FatorProducaoSubstancia (id_fator_producao,id_substancia,quantidade,unidade) VALUES (1,2,20,'kg');

-- TipoSensor
INSERT INTO TipoSensor (id_tipo_sensor,tipo_sensor) VALUES (1,'TS');
INSERT INTO TipoSensor (id_tipo_sensor,tipo_sensor) VALUES (2,'HS');
INSERT INTO TipoSensor (id_tipo_sensor,tipo_sensor) VALUES (3,'TA');
INSERT INTO TipoSensor (id_tipo_sensor,tipo_sensor) VALUES (4,'VV');
INSERT INTO TipoSensor (id_tipo_sensor,tipo_sensor) VALUES (5,'HA');
INSERT INTO TipoSensor (id_tipo_sensor,tipo_sensor) VALUES (6,'PL');
INSERT INTO TipoSensor (id_tipo_sensor,tipo_sensor) VALUES (7,'PA');

-- Sensor
INSERT INTO Sensor (id_sensor,identificador,id_tipo_sensor,valor_referencia) VALUES (1,'12345',1,10);
INSERT INTO Sensor (id_sensor,identificador,id_tipo_sensor,valor_referencia) VALUES (2,'HS',2,10);
INSERT INTO Sensor (id_sensor,identificador,id_tipo_sensor,valor_referencia) VALUES (3,'VS',3,10);
INSERT INTO Sensor (id_sensor,identificador,id_tipo_sensor,valor_referencia) VALUES (4,'DS',4,10);
INSERT INTO Sensor (id_sensor,identificador,id_tipo_sensor,valor_referencia) VALUES (5,'HA',5,10);
INSERT INTO Sensor (id_sensor,identificador,id_tipo_sensor,valor_referencia) VALUES (6,'PL',6,10);

-- MedicaoSensor
INSERT INTO MedicaoSensor (id_medicao,id_sensor,medicao,data_hora) VALUES (1,1,20,'10-Jan-2020 12:00:00');
INSERT INTO MedicaoSensor (id_medicao,id_sensor,medicao,data_hora) VALUES (2,2,60,'11-Jan-2020 12:00:00');
INSERT INTO MedicaoSensor (id_medicao,id_sensor,medicao,data_hora) VALUES (3,3,10, '12-Jan-2020 12:00:00');
INSERT INTO MedicaoSensor (id_medicao,id_sensor,medicao,data_hora) VALUES (4,4,10, '13-Jan-2020 12:00:00');
INSERT INTO MedicaoSensor (id_medicao,id_sensor,medicao,data_hora) VALUES (5,5,80, '14-Jan-2020 12:00:00');
INSERT INTO MedicaoSensor (id_medicao,id_sensor,medicao,data_hora) VALUES (6,6,5, '15-Jan-2020 12:00:00');

-- Plantacao
INSERT INTO Plantacao (id_plantacao,id_setor,id_cultura,data_inicio) VALUES (1,1,1,'01-Jan-2021');
INSERT INTO Plantacao (id_plantacao,id_setor,id_cultura,data_inicio) VALUES (2,2,2,'01-Feb-2021');
INSERT INTO Plantacao (id_plantacao,id_setor,id_cultura,data_inicio) VALUES (3,3,3,'01-Apr-2021');

-- Colheita
INSERT INTO Colheita (id_operacao,id_produto,quantidade,id_plantacao) VALUES (1,1,10,1);
INSERT INTO Colheita (id_operacao,id_produto,quantidade,id_plantacao) VALUES (2,2,10,2);
INSERT INTO Colheita (id_operacao,id_produto,quantidade,id_plantacao) VALUES (3,3,10,3);
INSERT INTO Colheita (id_operacao,id_produto,quantidade,id_plantacao) VALUES (4,1,10,1);

-- TipoAplicacao
INSERT INTO TipoAplicacao (id_tipo_aplicacao,tipo_aplicacao) VALUES (1,'Foliar');
INSERT INTO TipoAplicacao (id_tipo_aplicacao,tipo_aplicacao) VALUES (2,'Solo');

-- Aplicacao
INSERT INTO Aplicacao (id_operacao,id_setor,id_tipo_aplicacao) VALUES (1,1,1);
INSERT INTO Aplicacao (id_operacao,id_setor,id_tipo_aplicacao) VALUES (2,2,2);
INSERT INTO Aplicacao (id_operacao,id_setor,id_tipo_aplicacao) VALUES (5,3,1);

-- RestricaoAplicacao
INSERT INTO RestricaoAplicacao (id_setor,data_inicio,data_fim,id_tipo_fator_producao) VALUES (1,'01-Jan-2021','01-Feb-2021',1);
INSERT INTO RestricaoAplicacao (id_setor,data_inicio,data_fim,id_tipo_fator_producao) VALUES (2,'01-Feb-2021','01-Mar-2021',2);

-- FatorProducaoAplicacao
INSERT INTO FatorProducaoAplicacao (id_operacao,id_fator_producao,quantidade) VALUES (1,1,100);
INSERT INTO FatorProducaoAplicacao (id_operacao,id_fator_producao,quantidade) VALUES (2,2,100);
INSERT INTO FatorProducaoAplicacao (id_operacao,id_fator_producao,quantidade) VALUES (5,1,100);

-- InputSensor
INSERT INTO InputSensor (string) VALUES ('62943HS050090202301051035');
INSERT INTO InputSensor (string) VALUES ('62943TS050090202301061520');
INSERT INTO InputSensor (string) VALUES ('62943TS080090202302311035');
INSERT INTO InputSensor (string) VALUES ('62943DS080090202302071520');
INSERT INTO InputSensor (string) VALUES ('12345DS080090202302071520');
INSERT INTO InputSensor (string) VALUES (' ');

-- InputHub
INSERT INTO InputHub(string) VALUES ('CT1;40.6389;-8.6553;C1');
INSERT INTO InputHub(string) VALUES ('CT2;38.0333;-7.8833;C2');
INSERT INTO InputHub(string) VALUES ('CT10;39.7444;-8.8072;P3');

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
