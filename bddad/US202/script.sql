CREATE TABLE Setor (
  id_setor   number(10) GENERATED AS IDENTITY, 
  designacao varchar2(50) NOT NULL, 
  area       float(10) NOT NULL, 
  PRIMARY KEY (id_setor));
CREATE TABLE Cultura (
  id_cultura      number(2) GENERATED AS IDENTITY, 
  cultura         varchar2(50) NOT NULL, 
  id_tipo_cultura number(2) NOT NULL, 
  id_produto      number(8) NOT NULL, 
  PRIMARY KEY (id_cultura));
CREATE TABLE TipoCultura (
  id_tipo_cultura number(2) GENERATED AS IDENTITY, 
  tipo_cultura    varchar2(50) NOT NULL, 
  PRIMARY KEY (id_tipo_cultura));
CREATE TABLE FatorProducao (
  id_fator_producao      number(2) GENERATED AS IDENTITY, 
  id_tipo_fator_producao number(2) NOT NULL, 
  nome                   varchar2(50) NOT NULL, 
  id_tipo_formulacao     number(2) NOT NULL, 
  PRIMARY KEY (id_fator_producao));
CREATE TABLE TipoFatorProducao (
  id_tipo_fator_producao number(2) GENERATED AS IDENTITY, 
  tipo_fator_producao    varchar2(50) NOT NULL, 
  PRIMARY KEY (id_tipo_fator_producao));
CREATE TABLE Substancia (
  id_substancia number(2) GENERATED AS IDENTITY, 
  substancia    varchar2(50) NOT NULL, 
  PRIMARY KEY (id_substancia));
CREATE TABLE FatorProducaoSubstancia (
  id_fator_producao number(2) NOT NULL, 
  id_substancia     number(2) NOT NULL, 
  percentagem       number(3) NOT NULL, 
  PRIMARY KEY (id_fator_producao, 
  id_substancia));
CREATE TABLE TipoFormulacao (
  id_tipo_formulacao number(2) GENERATED AS IDENTITY, 
  tipo_formulacao    varchar2(50) NOT NULL, 
  PRIMARY KEY (id_tipo_formulacao));
CREATE TABLE PlanoRega (
  id_setor      number(10) NOT NULL, 
  data_inicio   timestamp(0) NOT NULL, 
  tempo         number(10) NOT NULL, 
  periodicidade number(10) NOT NULL, 
  id_tipo_rega  number(2) NOT NULL, 
  data_fim      timestamp(0), 
  PRIMARY KEY (id_setor, 
  data_inicio));
CREATE TABLE TipoRega (
  id_tpo_rega     number(2) GENERATED AS IDENTITY, 
  id_tipo_tubagem number(10) NOT NULL, 
  PRIMARY KEY (id_tpo_rega));
CREATE TABLE TipoTubagem (
  id_tipo_tubagem number(10) GENERATED AS IDENTITY, 
  tipo_tubagem    varchar2(50) NOT NULL, 
  PRIMARY KEY (id_tipo_tubagem));
CREATE TABLE Sensor (
  id_sensor        number(8) GENERATED AS IDENTITY, 
  identificador    varchar2(5) NOT NULL, 
  id_tipo_sensor   number(2) NOT NULL, 
  valor_referencia double precision NOT NULL, 
  PRIMARY KEY (id_sensor));
CREATE TABLE TipoSensor (
  id_tipo_sensor number(2) GENERATED AS IDENTITY, 
  tipo_sensor    varchar2(2) NOT NULL, 
  PRIMARY KEY (id_tipo_sensor));
CREATE TABLE Cliente (
  id_cliente         number(8) GENERATED AS IDENTITY, 
  nome               varchar2(50) NOT NULL, 
  nif                number(9) NOT NULL, 
  email              varchar2(250) NOT NULL, 
  morada             varchar2(80), 
  morada_entrega     varchar2(80) NOT NULL, 
  plafond            double precision NOT NULL, 
  cod_postal_entrega number(10) NOT NULL, 
  cod_postal         number(10) NOT NULL, 
  PRIMARY KEY (id_cliente));
CREATE TABLE ProdutoEncomenda (
  id_encomenda       number(8) NOT NULL, 
  id_produto         number(8) NOT NULL, 
  quantidade         number(4) NOT NULL, 
  preco_unitario     double precision NOT NULL, 
  iva                double precision NOT NULL, 
  designacao_produto varchar2(50) NOT NULL, 
  PRIMARY KEY (id_encomenda, 
  id_produto));
CREATE TABLE Produto (
  id_produto     number(8) GENERATED AS IDENTITY, 
  designacao     varchar2(50) NOT NULL, 
  preco          double precision NOT NULL, 
  id_escalao_iva number(2) NOT NULL, 
  PRIMARY KEY (id_produto));
CREATE TABLE Localidade (
  cod_postal number(10) GENERATED AS IDENTITY, 
  localidade varchar2(50) NOT NULL, 
  PRIMARY KEY (cod_postal));
CREATE TABLE EscalaoIva (
  id_escalao_iva number(2) GENERATED AS IDENTITY, 
  valor          double precision NOT NULL, 
  PRIMARY KEY (id_escalao_iva));
CREATE TABLE MedicaoSensor (
  id_sensor number(8) NOT NULL, 
  id_setor  number(10) NOT NULL, 
  medicao   number(3) NOT NULL, 
  data_hora timestamp(0) NOT NULL, 
  PRIMARY KEY (id_sensor, 
  id_setor));
CREATE TABLE Rega (
  id_setor     number(10) NOT NULL, 
  data         timestamp(0) NOT NULL, 
  id_tipo_rega number(2) NOT NULL, 
  PRIMARY KEY (id_setor, 
  data, 
  id_tipo_rega));
CREATE TABLE Colheita (
  id_produto number(8) NOT NULL, 
  data       timestamp(0) NOT NULL, 
  quantidade number(8) NOT NULL, 
  id_setor   number(10) NOT NULL, 
  PRIMARY KEY (id_produto, 
  data));
CREATE TABLE Encomenda (
  id_encomenda              number(8) GENERATED AS IDENTITY, 
  id_cliente                number(8) NOT NULL, 
  data_vencimento_pagamento timestamp(0) NOT NULL, 
  valor                     float(10) NOT NULL, 
  data_entrega              timestamp(0), 
  data_pagamento            timestamp(0), 
  morada_entrega            varchar2(80) NOT NULL, 
  cod_postal_entrega        number(10) NOT NULL, 
  PRIMARY KEY (id_encomenda));
CREATE TABLE Plantacao (
  id_setor    number(10) NOT NULL, 
  id_cultura  number(2) NOT NULL, 
  data_inicio timestamp(0) NOT NULL, 
  PRIMARY KEY (id_setor, 
  id_cultura, 
  data_inicio));
CREATE TABLE Edificio (
  id_edificio      number(10) GENERATED AS IDENTITY, 
  id_tipo_edificio number(3) NOT NULL, 
  PRIMARY KEY (id_edificio));
CREATE TABLE TipoEdificio (
  id_tipo_edificio number(3) GENERATED AS IDENTITY, 
  tipo_edificio    varchar2(50) NOT NULL, 
  PRIMARY KEY (id_tipo_edificio));
CREATE TABLE AplicacaoFatorProducao (
  id_setor                         number(10) NOT NULL, 
  id_fator_producao                number(2) NOT NULL, 
  data                             timestamp(0) NOT NULL, 
  id_tipo_aplicacao_fator_producao number(3) NOT NULL, 
  PRIMARY KEY (id_setor, 
  id_fator_producao, 
  data));
CREATE TABLE TipoAplicacaoFatorProducao (
  id_tipo_aplicacao_fator_producao number(3) GENERATED AS IDENTITY, 
  tipo_aplicacao_fator_producao    varchar2(50) NOT NULL, 
  PRIMARY KEY (id_tipo_aplicacao_fator_producao));
ALTER TABLE FatorProducaoSubstancia ADD CONSTRAINT FKFatorProdu760304 FOREIGN KEY (id_substancia) REFERENCES Substancia (id_substancia);
ALTER TABLE FatorProducaoSubstancia ADD CONSTRAINT FKFatorProdu994771 FOREIGN KEY (id_fator_producao) REFERENCES FatorProducao (id_fator_producao);
ALTER TABLE FatorProducao ADD CONSTRAINT FKFatorProdu929110 FOREIGN KEY (id_tipo_fator_producao) REFERENCES TipoFatorProducao (id_tipo_fator_producao);
ALTER TABLE FatorProducao ADD CONSTRAINT FKFatorProdu745587 FOREIGN KEY (id_tipo_formulacao) REFERENCES TipoFormulacao (id_tipo_formulacao);
ALTER TABLE Cultura ADD CONSTRAINT FKCultura145286 FOREIGN KEY (id_tipo_cultura) REFERENCES TipoCultura (id_tipo_cultura);
ALTER TABLE PlanoRega ADD CONSTRAINT FKPlanoRega644835 FOREIGN KEY (id_setor) REFERENCES Setor (id_setor);
ALTER TABLE PlanoRega ADD CONSTRAINT FKPlanoRega105418 FOREIGN KEY (id_tipo_rega) REFERENCES TipoRega (id_tpo_rega);
ALTER TABLE TipoRega ADD CONSTRAINT FKTipoRega396109 FOREIGN KEY (id_tipo_tubagem) REFERENCES TipoTubagem (id_tipo_tubagem);
ALTER TABLE Sensor ADD CONSTRAINT FKSensor979466 FOREIGN KEY (id_tipo_sensor) REFERENCES TipoSensor (id_tipo_sensor);
ALTER TABLE Cultura ADD CONSTRAINT FKCultura72246 FOREIGN KEY (id_produto) REFERENCES Produto (id_produto);
ALTER TABLE ProdutoEncomenda ADD CONSTRAINT FKProdutoEnc708380 FOREIGN KEY (id_produto) REFERENCES Produto (id_produto);
ALTER TABLE Cliente ADD CONSTRAINT FKCliente206820 FOREIGN KEY (cod_postal_entrega) REFERENCES Localidade (cod_postal);
ALTER TABLE Produto ADD CONSTRAINT FKProduto445137 FOREIGN KEY (id_escalao_iva) REFERENCES EscalaoIva (id_escalao_iva);
ALTER TABLE MedicaoSensor ADD CONSTRAINT FKMedicaoSen844882 FOREIGN KEY (id_sensor) REFERENCES Sensor (id_sensor);
ALTER TABLE MedicaoSensor ADD CONSTRAINT FKMedicaoSen825065 FOREIGN KEY (id_setor) REFERENCES Setor (id_setor);
ALTER TABLE Rega ADD CONSTRAINT FKRega852702 FOREIGN KEY (id_tipo_rega) REFERENCES TipoRega (id_tpo_rega);
ALTER TABLE Rega ADD CONSTRAINT FKRega392120 FOREIGN KEY (id_setor) REFERENCES Setor (id_setor);
ALTER TABLE Colheita ADD CONSTRAINT FKColheita890158 FOREIGN KEY (id_produto) REFERENCES Produto (id_produto);
ALTER TABLE Colheita ADD CONSTRAINT FKColheita958985 FOREIGN KEY (id_setor) REFERENCES Setor (id_setor);
ALTER TABLE ProdutoEncomenda ADD CONSTRAINT FKProdutoEnc488678 FOREIGN KEY (id_encomenda) REFERENCES Encomenda (id_encomenda);
ALTER TABLE Encomenda ADD CONSTRAINT FKEncomenda598842 FOREIGN KEY (id_cliente) REFERENCES Cliente (id_cliente);
ALTER TABLE Plantacao ADD CONSTRAINT FKPlantacao582454 FOREIGN KEY (id_setor) REFERENCES Setor (id_setor);
ALTER TABLE Plantacao ADD CONSTRAINT FKPlantacao141907 FOREIGN KEY (id_cultura) REFERENCES Cultura (id_cultura);
ALTER TABLE Cliente ADD CONSTRAINT FKCliente909904 FOREIGN KEY (cod_postal) REFERENCES Localidade (cod_postal);
ALTER TABLE Edificio ADD CONSTRAINT FKEdificio810117 FOREIGN KEY (id_tipo_edificio) REFERENCES TipoEdificio (id_tipo_edificio);
ALTER TABLE AplicacaoFatorProducao ADD CONSTRAINT FKAplicacaoF489632 FOREIGN KEY (id_setor) REFERENCES Setor (id_setor);
ALTER TABLE AplicacaoFatorProducao ADD CONSTRAINT FKAplicacaoF82233 FOREIGN KEY (id_fator_producao) REFERENCES FatorProducao (id_fator_producao);
ALTER TABLE AplicacaoFatorProducao ADD CONSTRAINT FKAplicacaoF203895 FOREIGN KEY (id_tipo_aplicacao_fator_producao) REFERENCES TipoAplicacaoFatorProducao (id_tipo_aplicacao_fator_producao);
ALTER TABLE Encomenda ADD CONSTRAINT FKEncomenda991394 FOREIGN KEY (cod_postal_entrega) REFERENCES Localidade (cod_postal);
