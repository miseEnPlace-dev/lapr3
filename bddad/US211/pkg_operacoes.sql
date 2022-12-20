CREATE OR REPLACE PACKAGE operacoes_nao_realizadas AS

  PROCEDURE cancel_operacao (id_operacao Operacao.id_operacao%TYPE);
  PROCEDURE atualizar_operacao (id_operacao Operacao.id_operacao%TYPE);

END operacoes_nao_realizadas;
