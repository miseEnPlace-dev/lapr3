-- Snowflake data model --

-- Descrição do modelo de dados:
-- Fact table são as tabelas que contêm os dados de negócio. (Ex: Vendas, Produção, etc.)
-- Dimensões são tabelas que contêm os dados de contexto. (Ex: Tempo, ano, mes, Cliente, Produto, etc.)

-- O modelo de dados é composto por 8 dimensões e 2 fact tables.
-- A dimensão Tempo é composta por 3 atributos: ano, mes e id_tempo.
-- A dimensão ano é composta por 1 atributo: ano.
-- A dimensão mes é composta por 1 atributo: mes.
-- A dimensão Cliente é composta por 3 atributos: id_cliente, nome e nif.
-- A dimensão Produto é composta por 3 atributos: id_produto, tipo e designacao.
-- A dimensão DesignacaoProduto é composta por 1 atributo: designacao.
-- A dimensão TipoProduto é composta por 1 atributo: tipo.
-- A dimensão Setor é composta por 2 atributos: id_setor e nome.
-- A fact table Venda é composta por 6 atributos: id_venda, id_cliente, id_produto, id_setor, id_tempo e quantidade.
-- A fact table Producao é composta por 5 atributos: id_producao, id_produto, id_setor, id_tempo e quantidade.

-- Este esquema 'SnowFlake' tem as hierarquias divididas em dimensões separadas.
-- Existe pouca redundância de dados, pois os atributos que se repetem são referenciados por chaves estrangeiras.
-- Para buscar os dados de uma fact table, é necessário fazer muitos joins com as dimensões.
-- É um DB complexa.


-- DROP TABLES --
DROP TABLE Cliente CASCADE CONSTRAINTS PURGE;
DROP TABLE DesignacaoProduto CASCADE CONSTRAINTS PURGE;
DROP TABLE TipoProduto CASCADE CONSTRAINTS PURGE;
DROP TABLE Produto CASCADE CONSTRAINTS PURGE;
DROP TABLE Setor CASCADE CONSTRAINTS PURGE;
DROP TABLE ano CASCADE CONSTRAINTS PURGE;
DROP TABLE mes CASCADE CONSTRAINTS PURGE;
DROP TABLE Tempo CASCADE CONSTRAINTS PURGE;
DROP TABLE Venda CASCADE CONSTRAINTS PURGE;
DROP TABLE Producao CASCADE CONSTRAINTS PURGE;


-- Dimension tables --
CREATE TABLE Cliente (
  id_cliente NUMBER(10) NOT NULL,
  nome VARCHAR2(50) NOT NULL,
  nif NUMBER(9) NOT NULL,
  PRIMARY KEY (id_cliente),
  CONSTRAINT CHK_Nif    CHECK (nif > 100000000 AND nif < 999999999)
);

CREATE TABLE DesignacaoProduto (
  designacao VARCHAR2(50) NOT NULL,
  PRIMARY KEY (designacao)
);

CREATE TABLE TipoProduto (
  tipo VARCHAR2(50) NOT NULL,
  PRIMARY KEY (tipo)
);

CREATE TABLE Produto (
  id_produto NUMBER(10) NOT NULL,
  tipo VARCHAR2(50) NOT NULL,
  designacao VARCHAR2(50) NOT NULL,
  PRIMARY KEY (id_produto),
  FOREIGN KEY (designacao) REFERENCES DesignacaoProduto (designacao),
  FOREIGN KEY (tipo) REFERENCES TipoProduto (tipo)
);


CREATE TABLE Setor (
  id_setor NUMBER(10) NOT NULL,
  nome VARCHAR(50) NOT NULL,
  PRIMARY KEY (id_setor)
);

CREATE TABLE ano (
  ano NUMBER(4) NOT NULL,
  PRIMARY KEY (ano),
  CONSTRAINT CHK_Ano    CHECK (ano > 0)
);

CREATE TABLE mes (
  mes NUMBER(2) NOT NULL,
  PRIMARY KEY (mes),
  CONSTRAINT CHK_Mes    CHECK (mes > 0 AND mes < 13)
);

CREATE TABLE Tempo (
  id_tempo NUMBER(10) NOT NULL,
  ano NUMBER(4) NOT NULL,
  mes NUMBER(2) NOT NULL,
  PRIMARY KEY (id_tempo),
  FOREIGN KEY (ano) REFERENCES ano (ano),
  FOREIGN KEY (mes) REFERENCES mes (mes)
);

-- Fact table --

CREATE TABLE Venda (
  id_venda NUMBER(10) NOT NULL,
  id_cliente NUMBER(10) NOT NULL,
  id_produto NUMBER(10) NOT NULL,
  id_setor NUMBER(10) NOT NULL,
  id_tempo NUMBER(10) NOT NULL,
  quantidade NUMBER(10) NOT NULL,
  PRIMARY KEY (id_venda),
  FOREIGN KEY (id_cliente) REFERENCES Cliente (id_cliente),
  FOREIGN KEY (id_produto) REFERENCES Produto (id_produto),
  FOREIGN KEY (id_setor) REFERENCES Setor (id_setor),
  FOREIGN KEY (id_tempo) REFERENCES Tempo (id_tempo),
  CONSTRAINT CHK_Quantidade    CHECK (quantidade > 0)
);

CREATE TABLE Producao (
  id_producao NUMBER(10) NOT NULL,
  id_produto NUMBER(10) NOT NULL,
  id_setor NUMBER(10) NOT NULL,
  id_tempo NUMBER(10) NOT NULL,
  quantidade NUMBER(10) NOT NULL,
  PRIMARY KEY (id_producao),
  FOREIGN KEY (id_produto) REFERENCES Produto (id_produto),
  FOREIGN KEY (id_setor) REFERENCES Setor (id_setor),
  FOREIGN KEY (id_tempo) REFERENCES Tempo (id_tempo),
  CONSTRAINT CHK_Quantidade_Prod    CHECK (quantidade > 0)
);
