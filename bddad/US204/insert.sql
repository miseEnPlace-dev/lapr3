DROP TABLE Setor CASCADE CONSTRAINTS PURGE;
DROP TABLE Cultura CASCADE CONSTRAINTS PURGE;
DROP TABLE TipoCultura CASCADE CONSTRAINTS PURGE;
DROP TABLE FatorProducao CASCADE CONSTRAINTS PURGE;
DROP TABLE TipoFatorProducao CASCADE CONSTRAINTS PURGE;
DROP TABLE Substancia CASCADE CONSTRAINTS PURGE;
DROP TABLE FatorProducaoSubstancia CASCADE CONSTRAINTS PURGE;
DROP TABLE TipoFormulacao CASCADE CONSTRAINTS PURGE;
DROP TABLE PlanoRega CASCADE CONSTRAINTS PURGE;
DROP TABLE TipoRega CASCADE CONSTRAINTS PURGE;
DROP TABLE TipoTubagem CASCADE CONSTRAINTS PURGE;
DROP TABLE Sensor CASCADE CONSTRAINTS PURGE;
DROP TABLE TipoSensor CASCADE CONSTRAINTS PURGE;
DROP TABLE Cliente CASCADE CONSTRAINTS PURGE;
DROP TABLE ProdutoEncomenda CASCADE CONSTRAINTS PURGE;
DROP TABLE Produto CASCADE CONSTRAINTS PURGE;
DROP TABLE Localidade CASCADE CONSTRAINTS PURGE;
DROP TABLE EscalaoIva CASCADE CONSTRAINTS PURGE;
DROP TABLE MedicaoSensor CASCADE CONSTRAINTS PURGE;
DROP TABLE Rega CASCADE CONSTRAINTS PURGE;
DROP TABLE Colheita CASCADE CONSTRAINTS PURGE;
DROP TABLE Encomenda CASCADE CONSTRAINTS PURGE;
DROP TABLE Plantacao CASCADE CONSTRAINTS PURGE;
DROP TABLE Edificio CASCADE CONSTRAINTS PURGE;
DROP TABLE TipoEdificio CASCADE CONSTRAINTS PURGE;
DROP TABLE AplicacaoFatorProducao CASCADE CONSTRAINTS PURGE;
DROP TABLE TipoAplicacaoFatorProducao CASCADE CONSTRAINTS PURGE;
DROP TABLE ProdutoCultura CASCADE CONSTRAINTS PURGE;
DROP TABLE Fornecedor CASCADE CONSTRAINTS PURGE;
DROP TABLE CategoriaSubstancia CASCADE CONSTRAINTS PURGE;

CREATE TABLE Setor (
  id_setor   number(10),
  designacao varchar2(50) NOT NULL,
  area       float(10) NOT NULL,
  CONSTRAINT CHK_Setor_PositiveArea CHECK (area >= 0),
  PRIMARY KEY (id_setor)
);

CREATE TABLE EscalaoIva (
  id_escalao_iva number(2),
  valor          double precision NOT NULL,
  CONSTRAINT CHK_EscalaoIva_PositiveIva CHECK (valor >= 0),
  PRIMARY KEY (id_escalao_iva)
);

CREATE TABLE Produto (
  id_produto     number(8),
  designacao     varchar2(50) NOT NULL,
  preco          double precision NOT NULL,
  id_escalao_iva number(2) NOT NULL,
  CONSTRAINT CHK_Produto_PositivePreco CHECK (preco >= 0),
  PRIMARY KEY (id_produto),
  FOREIGN KEY (id_escalao_iva) REFERENCES EscalaoIva (id_escalao_iva) ON DELETE CASCADE
);

CREATE TABLE TipoCultura (
  id_tipo_cultura number(2),
  tipo_cultura    varchar2(50) NOT NULL,
  PRIMARY KEY (id_tipo_cultura)
);

CREATE TABLE Cultura (
  id_cultura      number(8),
  cultura         varchar2(50) NOT NULL,
  id_tipo_cultura number(2) NOT NULL,
  PRIMARY KEY (id_cultura),
  FOREIGN KEY (id_tipo_cultura) REFERENCES TipoCultura (id_tipo_cultura) ON DELETE CASCADE
);

CREATE TABLE TipoFatorProducao (
  id_tipo_fator_producao number(2),
  tipo_fator_producao    varchar2(50) NOT NULL,
  PRIMARY KEY (id_tipo_fator_producao)
);

CREATE TABLE Fornecedor (
  id_fornecedor number(8),
  fornecedor    varchar2(50) NOT NULL,
  PRIMARY KEY (id_fornecedor)
);

CREATE TABLE CategoriaSubstancia (
  id_categoria_substancia number(8),
  categoria_substancia    varchar2(50) NOT NULL,
  PRIMARY KEY (id_categoria_substancia)
);


CREATE TABLE Substancia (
  id_substancia number(2),
  substancia    varchar2(50) NOT NULL,
  id_fornecedor           number(8) NOT NULL,
  id_categoria_substancia number(8) NOT NULL,
  PRIMARY KEY (id_substancia),
  FOREIGN KEY (id_fornecedor) REFERENCES Fornecedor (id_fornecedor),
  FOREIGN KEY (id_categoria_substancia) REFERENCES CategoriaSubstancia (id_categoria_substancia)
);

CREATE TABLE TipoFormulacao (
  id_tipo_formulacao number(2),
  tipo_formulacao    varchar2(50) NOT NULL,
  PRIMARY KEY (id_tipo_formulacao)
);

CREATE TABLE FatorProducao (
  id_fator_producao      number(2),
  id_tipo_fator_producao number(2) NOT NULL,
  nome                   varchar2(50) NOT NULL,
  id_tipo_formulacao     number(2) NOT NULL,
  PRIMARY KEY (id_fator_producao),
  FOREIGN KEY (id_tipo_fator_producao) REFERENCES TipoFatorProducao (id_tipo_fator_producao) ON DELETE CASCADE,
  FOREIGN KEY (id_tipo_formulacao) REFERENCES TipoFormulacao (id_tipo_formulacao) ON DELETE CASCADE
);

CREATE TABLE FatorProducaoSubstancia (
  id_fator_producao number(2) NOT NULL,
  id_substancia     number(2) NOT NULL,
  quantidade        number(3) NOT NULL,
  unidades          varchar2(50) NOT NULL,
  CONSTRAINT CHK_FatorProducaoSubstancia_PositiveQuantidade CHECK (quantidade > 0),
  PRIMARY KEY (id_fator_producao, id_substancia),
  FOREIGN KEY (id_substancia) REFERENCES Substancia (id_substancia) ON DELETE CASCADE,
  FOREIGN KEY (id_fator_producao) REFERENCES FatorProducao (id_fator_producao) ON DELETE CASCADE
);

CREATE TABLE TipoTubagem (
  id_tipo_tubagem number(10),
  tipo_tubagem    varchar2(50) NOT NULL,
  PRIMARY KEY (id_tipo_tubagem)
);

CREATE TABLE TipoRega (
  id_tipo_rega     number(2),
  id_tipo_tubagem number(10) NOT NULL,
  PRIMARY KEY (id_tipo_rega),
  FOREIGN KEY (id_tipo_tubagem) REFERENCES TipoTubagem (id_tipo_tubagem) ON DELETE CASCADE
);

CREATE TABLE PlanoRega (
  id_setor      number(10) NOT NULL,
  data_inicio   timestamp(0) NOT NULL,
  tempo         number(10) NOT NULL,
  periodicidade number(10) NOT NULL,
  id_tipo_rega  number(2) NOT NULL,
  data_fim      timestamp(0),
  PRIMARY KEY (id_setor, data_inicio),
  CONSTRAINT CHK_PlanoRega_PositiveTempo CHECK (tempo > 0),
  CONSTRAINT CHK_PlanoRega_PositivePeriodicidade CHECK (periodicidade > 0),
  FOREIGN KEY (id_setor) REFERENCES Setor (id_setor) ON DELETE CASCADE,
  FOREIGN KEY (id_tipo_rega) REFERENCES TipoRega (id_tipo_rega) ON DELETE CASCADE
);

CREATE TABLE TipoSensor (
  id_tipo_sensor number(2),
  tipo_sensor    varchar2(2) NOT NULL,
  PRIMARY KEY (id_tipo_sensor)
);

CREATE TABLE Sensor (
  id_sensor        number(8),
  identificador    varchar2(5) NOT NULL,
  id_tipo_sensor   number(2) NOT NULL,
  valor_referencia double precision NOT NULL,
  PRIMARY KEY (id_sensor),
  CONSTRAINT CHK_Sensor_PositiveValorReferencia CHECK (valor_referencia > 0),
  FOREIGN KEY (id_tipo_sensor) REFERENCES TipoSensor (id_tipo_sensor) ON DELETE CASCADE
);

CREATE TABLE Localidade (
  cod_postal varchar2(8),
  localidade varchar2(50) NOT NULL,
  CONSTRAINT CHK_Localidade_CodigoPostal CHECK(REGEXP_LIKE(cod_postal,'\d{4}-\d{3}') ),
  PRIMARY KEY (cod_postal)
);

CREATE TABLE Cliente (
  id_cliente         number(8),
  nome               varchar2(50) NOT NULL,
  nif                number(9) NOT NULL,
  email              varchar2(250) NOT NULL,
  morada             varchar2(80),
  morada_entrega     varchar2(80) NOT NULL,
  plafond            double precision NOT NULL,
  cod_postal_entrega varchar2(8) NOT NULL,
  cod_postal         varchar2(8) NOT NULL,
  n_encomendas           number(3) DEFAULT 0 NOT NULL,
  valor_total_encomendas number(10) DEFAULT 0 NOT NULL,
  PRIMARY KEY (id_cliente),
  CONSTRAINT CHK_Nif    CHECK (nif > 100000000 AND nif < 999999999),
  CONSTRAINT CHK_Email CHECK (email like '%___@___%.__%'),
  CONSTRAINT CHK_Cliente_NonNegativePlafond CHECK (plafond >= 0),
  CONSTRAINT CHK_Cliente_NonNegativeEncomendas CHECK (n_encomendas >= 0),
  CONSTRAINT CHK_Cliente_NonNegativeValorTotal CHECK (valor_total_encomendas >= 0),
  FOREIGN KEY (cod_postal_entrega) REFERENCES Localidade (cod_postal) ON DELETE CASCADE,
  FOREIGN KEY (cod_postal) REFERENCES Localidade (cod_postal) ON DELETE CASCADE
);

CREATE TABLE Encomenda (
  id_encomenda              number(8),
  id_cliente                number(8) NOT NULL,
  data_vencimento_pagamento timestamp(0) NOT NULL,
  data_registo              timestamp(0) NOT NULL,
  data_entrega              timestamp(0),
  data_pagamento            timestamp(0),
  morada_entrega            varchar2(80) NOT NULL,
  cod_postal_entrega        varchar2(8) NOT NULL,
  PRIMARY KEY (id_encomenda),
  CONSTRAINT CHK_Encomenda_DataVencimentoPagamento CHECK (data_vencimento_pagamento > data_registo),
  CONSTRAINT CHK_Encomenda_DataEntrega CHECK (data_entrega > data_registo),
  CONSTRAINT CHK_Encomenda_DataPagamento CHECK (data_pagamento > data_registo),
  FOREIGN KEY (id_cliente) REFERENCES Cliente (id_cliente) ON DELETE CASCADE,
  FOREIGN KEY (cod_postal_entrega) REFERENCES Localidade (cod_postal) ON DELETE CASCADE
);

CREATE TABLE ProdutoEncomenda (
  id_encomenda       number(8) NOT NULL,
  id_produto         number(8) NOT NULL,
  quantidade         number(4) NOT NULL,
  preco_unitario     double precision NOT NULL,
  iva                double precision NOT NULL,
  designacao_produto varchar2(50) NOT NULL,
  PRIMARY KEY (id_encomenda, id_produto),
  CONSTRAINT CHK_ProdutoEncomenda_PositiveQuantidade CHECK (quantidade > 0),
  CONSTRAINT CHK_ProdutoEncomenda_PositivePrecoUnitario CHECK (preco_unitario > 0),
  CONSTRAINT CHK_ProdutoEncomenda_PositiveIva CHECK (iva > 0),
  FOREIGN KEY (id_produto) REFERENCES Produto (id_produto) ON DELETE CASCADE,
  FOREIGN KEY (id_encomenda) REFERENCES Encomenda (id_encomenda) ON DELETE CASCADE
);

CREATE TABLE MedicaoSensor (
  id_sensor number(8) NOT NULL,
  id_setor  number(10) NOT NULL,
  medicao   number(3) NOT NULL,
  data_hora timestamp(0) NOT NULL,
  PRIMARY KEY (id_sensor, id_setor),
  FOREIGN KEY (id_sensor) REFERENCES Sensor (id_sensor) ON DELETE CASCADE,
  FOREIGN KEY (id_setor) REFERENCES Setor (id_setor) ON DELETE CASCADE
);

CREATE TABLE Rega (
  id_setor     number(10) NOT NULL,
  data         timestamp(0) NOT NULL,
  id_tipo_rega number(2) NOT NULL,
  PRIMARY KEY (id_setor, data, id_tipo_rega),
  FOREIGN KEY (id_tipo_rega) REFERENCES TipoRega (id_tipo_rega) ON DELETE CASCADE,
  FOREIGN KEY (id_setor) REFERENCES Setor (id_setor) ON DELETE CASCADE
);

CREATE TABLE Colheita (
  id_produto number(8) NOT NULL,
  data       timestamp(0) NOT NULL,
  quantidade number(8) NOT NULL,
  id_setor   number(10) NOT NULL,
  PRIMARY KEY (id_produto, data),
  CONSTRAINT CHK_Colheita_PositiveQuantidade CHECK (quantidade > 0),
  FOREIGN KEY (id_produto) REFERENCES Produto (id_produto) ON DELETE CASCADE,
  FOREIGN KEY (id_setor) REFERENCES Setor (id_setor) ON DELETE CASCADE
);

CREATE TABLE Plantacao (
  id_setor    number(10) NOT NULL,
  id_cultura  number(8) NOT NULL,
  data_inicio timestamp(0) NOT NULL,
  PRIMARY KEY (id_setor, id_cultura, data_inicio),
  FOREIGN KEY (id_setor) REFERENCES Setor (id_setor) ON DELETE CASCADE,
  FOREIGN KEY (id_cultura) REFERENCES Cultura (id_cultura) ON DELETE CASCADE
);

CREATE TABLE TipoEdificio (
  id_tipo_edificio number(3),
  tipo_edificio    varchar2(50) NOT NULL,
  PRIMARY KEY (id_tipo_edificio)
);

CREATE TABLE Edificio (
  id_edificio      number(10),
  id_tipo_edificio number(3) NOT NULL,
  PRIMARY KEY (id_edificio),
  FOREIGN KEY (id_tipo_edificio) REFERENCES TipoEdificio (id_tipo_edificio) ON DELETE CASCADE
);

CREATE TABLE TipoAplicacaoFatorProducao (
  id_tipo_aplicacao_fator_producao number(3),
  tipo_aplicacao_fator_producao    varchar2(50) NOT NULL,
  PRIMARY KEY (id_tipo_aplicacao_fator_producao)
);

CREATE TABLE AplicacaoFatorProducao (
  id_setor                         number(10) NOT NULL,
  id_fator_producao                number(2) NOT NULL,
  data                             timestamp(0) NOT NULL,
  id_tipo_aplicacao_fator_producao number(3) NOT NULL,
  PRIMARY KEY (id_setor, id_fator_producao, data),
  FOREIGN KEY (id_setor) REFERENCES Setor (id_setor) ON DELETE CASCADE,
  FOREIGN KEY (id_fator_producao) REFERENCES FatorProducao (id_fator_producao) ON DELETE CASCADE,
  FOREIGN KEY (id_tipo_aplicacao_fator_producao) REFERENCES TipoAplicacaoFatorProducao (id_tipo_aplicacao_fator_producao) ON DELETE CASCADE
);

CREATE TABLE ProdutoCultura (
  id_cultura number(8) NOT NULL,
  id_produto number(8) NOT NULL,
  PRIMARY KEY (id_cultura, id_produto),
  FOREIGN KEY (id_cultura) REFERENCES Cultura (id_cultura) ON DELETE CASCADE,
  FOREIGN KEY (id_produto) REFERENCES Produto (id_produto) ON DELETE CASCADE
);


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

-- Fornecedor
INSERT INTO Fornecedor (id_fornecedor,fornecedor) VALUES (1,'Alberto');
INSERT INTO Fornecedor (id_fornecedor,fornecedor) VALUES (2,'Joaquim');

-- CategoriaSubstancia
INSERT INTO CategoriaSubstancia (id_categoria_substancia,categoria_substancia) VALUES (1,'Materia Organica 1');
INSERT INTO CategoriaSubstancia (id_categoria_substancia,categoria_substancia) VALUES (2,'Materia Organica 1');
INSERT INTO CategoriaSubstancia (id_categoria_substancia,categoria_substancia) VALUES (3,'Materia Organica 1');
INSERT INTO CategoriaSubstancia (id_categoria_substancia,categoria_substancia) VALUES (4,'Materia Organica 1');
INSERT INTO CategoriaSubstancia (id_categoria_substancia,categoria_substancia) VALUES (5,'Materia Organica 1');

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
INSERT INTO FatorProducaoSubstancia (id_fator_producao,id_substancia,quantidade,unidades) VALUES (1,1,60,'kg');
INSERT INTO FatorProducaoSubstancia (id_fator_producao,id_substancia,quantidade,unidades) VALUES (1,2,20,'kg');

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
