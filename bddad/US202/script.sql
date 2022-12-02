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

CREATE TABLE Setor (
  id_setor   number(10),
  designacao varchar2(50) NOT NULL,
  area       float(10) NOT NULL,
  PRIMARY KEY (id_setor)
);

CREATE TABLE EscalaoIva (
  id_escalao_iva number(2),
  valor          double precision NOT NULL,
  PRIMARY KEY (id_escalao_iva)
);

CREATE TABLE Produto (
  id_produto     number(8),
  designacao     varchar2(50) NOT NULL,
  preco          double precision NOT NULL,
  id_escalao_iva number(2) NOT NULL,
  PRIMARY KEY (id_produto),
  FOREIGN KEY (id_escalao_iva) REFERENCES EscalaoIva (id_escalao_iva) ON DELETE CASCADE
);

CREATE TABLE TipoCultura (
  id_tipo_cultura number(2),
  tipo_cultura    varchar2(50) NOT NULL,
  PRIMARY KEY (id_tipo_cultura)
);

CREATE TABLE Cultura (
  id_cultura      number(2),
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

CREATE TABLE Substancia (
  id_substancia number(2),
  substancia    varchar2(50) NOT NULL,
  PRIMARY KEY (id_substancia)
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
  percentagem       number(3) NOT NULL,
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
  FOREIGN KEY (id_tipo_sensor) REFERENCES TipoSensor (id_tipo_sensor) ON DELETE CASCADE
);

CREATE TABLE Localidade (
  cod_postal varchar2(8),
  localidade varchar2(50) NOT NULL,
  CONSTRAINT ck_codigos_postais_cod_postal CHECK(REGEXP_LIKE(cod_postal,'\d{4}-\d{3}') ),
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
  FOREIGN KEY (cod_postal_entrega) REFERENCES Localidade (cod_postal) ON DELETE CASCADE,
  FOREIGN KEY (cod_postal) REFERENCES Localidade (cod_postal) ON DELETE CASCADE,
  CONSTRAINT chk_email_clt  CHECK (email like '%___@___%.__%'),
  CONSTRAINT chk_nif_clt    CHECK (nif > 100000000 AND nif < 999999999)
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
  FOREIGN KEY (id_produto) REFERENCES Produto (id_produto) ON DELETE CASCADE,
  FOREIGN KEY (id_setor) REFERENCES Setor (id_setor) ON DELETE CASCADE
);

CREATE TABLE Plantacao (
  id_setor    number(10) NOT NULL,
  id_cultura  number(2) NOT NULL,
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
