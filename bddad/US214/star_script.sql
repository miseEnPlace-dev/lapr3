-- Star data model --

-- Dimension tables --
CREATE TABLE Cliente (
  id_cliente NUMBER(10) NOT NULL,
  nome VARCHAR2(50) NOT NULL,
  nif NUMBER(9) NOT NULL,
  PRIMARY KEY (id_cliente),
  CONSTRAINT CHK_Nif    CHECK (nif > 100000000 AND nif < 999999999)
);

CREATE TABLE Produto (
  id_produto NUMBER(10) NOT NULL,
  tipo VARCHAR2(50) NOT NULL,
  designacao VARCHAR2(50) NOT NULL,
  PRIMARY KEY (id_produto)
);

CREATE TABLE Setor (
  id_setor NUMBER(10) NOT NULL,
  nome VARCHAR2(50) NOT NULL,
  PRIMARY KEY (id_setor)
);

CREATE TABLE Tempo (
  id_tempo NUMBER(10) NOT NULL,
  ano NUMBER(4) NOT NULL,
  mes NUMBER(2) NOT NULL,
  PRIMARY KEY (id_tempo),
  CONSTRAINT CHK_Mes    CHECK (mes > 0 AND mes < 13)
  CONSTRAINT CHK_Ano    CHECK (ano > 0)
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
  CONSTRAINT FK_Venda_Cliente    FOREIGN KEY (id_cliente) REFERENCES Cliente (id_cliente),
  CONSTRAINT FK_Venda_Produto    FOREIGN KEY (id_produto) REFERENCES Produto (id_produto),
  CONSTRAINT FK_Venda_Setor    FOREIGN KEY (id_setor) REFERENCES Setor (id_setor),
  CONSTRAINT FK_Venda_Tempo    FOREIGN KEY (id_tempo) REFERENCES Tempo (id_tempo),
  CONSTRAINT CHK_Quantidade    CHECK (quantidade > 0)
);

CREATE TABLE Producao (
  id_producao NUMBER (10) NOT NULL,
  id_produto NUMBER(10) NOT NULL,
  id_setor NUMBER(10) NOT NULL,
  id_tempo NUMBER(10) NOT NULL,
  quantidade NUMBER(10) NOT NULL,
  PRIMARY KEY (id_producao),
  CONSTRAINT FK_Producao_Produto    FOREIGN KEY (id_produto) REFERENCES Produto (id_produto),
  CONSTRAINT FK_Producao_Setor    FOREIGN KEY (id_setor) REFERENCES Setor (id_setor),
  CONSTRAINT FK_Producao_Tempo    FOREIGN KEY (id_tempo) REFERENCES Tempo (id_tempo),
  CONSTRAINT CHK_Quantidade    CHECK (quantidade > 0)
);




