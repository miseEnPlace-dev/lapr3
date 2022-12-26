-- Snowflake data model --

-- Dimension tables --
CREATE TABLE Cliente (
  id_cliente NUMBER(10) NOT NULL,
  nome VARCHAR2(50) NOT NULL,
  nif NUMBER(9) NOT NULL,
  PRIMARY KEY (id_cliente),
  CONSTRAINT CHK_Nif    CHECK (nif > 100000000 AND nif < 999999999)
);

CREATE TABLE NomeProduto (
  nome VARCHAR2(50) NOT NULL,
  PRIMARY KEY (nome)
);

CREATE TABLE TipoProduto (
  tipo VARCHAR2(25) NOT NULL,
  PRIMARY KEY (tipo)
);

CREATE TABLE Produto (
  id_produto NUMBER(10) NOT NULL,
  designacao VARCHAR2(50) NOT NULL,
  tipo VARCHAR2(25) NOT NULL,
  PRIMARY KEY (id_produto),
  FOREIGN KEY (designacao) REFERENCES NomeProduto (nome),
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
  CONSTRAINT CHK_Quantidade    CHECK (quantidade > 0)
);
