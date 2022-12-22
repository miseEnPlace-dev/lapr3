CREATE OR REPLACE PACKAGE operacoes AS
  TYPE produtos IS TABLE OF NUMBER INDEX BY BINARY_INTEGER;

  PROCEDURE cancel_operacao (operacao_id Operacao.id_operacao%TYPE);

  PROCEDURE atualizar_operacao_datas (operacao_id Operacao.id_operacao%TYPE, data_nova TIMESTAMP);

  PROCEDURE atualizar_operacao_produtos (operacao_id Operacao.id_operacao%TYPE, lista_produtos produtos);

END operacoes;
