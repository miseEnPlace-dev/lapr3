CREATE OR REPLACE PACKAGE operacoes_nao_realizadas AS

  PROCEDURE cancel_operacao;
  PROCEDURE atualizar_operacao;

END operacoes_nao_realizadas;
