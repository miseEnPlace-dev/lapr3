CREATE OR REPLACE PACKAGE operacoes AS

  PROCEDURE cancel_operacao (id_operacao Operacao.id_operacao%TYPE);

  PROCEDURE atualizar_operacao_datas (id_operacao Operacao.id_operacao%TYPE, data_nova TIMESTAMP);

END operacoes;
