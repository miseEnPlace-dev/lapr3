CREATE OR REPLACE PACKAGE operacoes AS

  PROCEDURE cancel_operacao (operacao_id Operacao.id_operacao%TYPE);

  PROCEDURE atualizar_operacao_datas (operacao_id Operacao.id_operacao%TYPE, data_nova TIMESTAMP);

END operacoes;
