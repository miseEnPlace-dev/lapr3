CREATE OR REPLACE PACKAGE operacoes_nao_realizadas AS
  TYPE produtos IS TABLE OF NUMBER INDEX BY BINARY_INTEGER;

  PROCEDURE cancel_operacao (id_operacao Operacao.id_operacao%TYPE);
  PROCEDURE atualizar_operacao_produtos (lista_produtos produtos, id_operacao Operacao.id_operacao%TYPE);
  PROCEDURE atualizar_operacao_datas (id_operacao Operacao.id_operacao%TYPE, data_nova TIMESTAMP);

END operacoes_nao_realizadas;
