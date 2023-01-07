CREATE OR REPLACE PACKAGE remarcar_operacoes AS

  -- Procedimento para cancelar uma operacao (colheita, rega ou aplicação de fatores de produção)
  -- Entende-se por cancelar uma operação deixar a data prevista nula e a data da operacao nula
  PROCEDURE cancelar_operacao (operacao_id Operacao.id_operacao%TYPE);

  -- Procedimento para atualizar a data de uma operação (colheita, rega ou aplicação de fatores de produção)
  PROCEDURE atualizar_data_prevista_operacao (operacao_id Operacao.id_operacao%TYPE, data_nova TIMESTAMP);

  -- Procedimento para atualizar a quantidade de uma colheita
  PROCEDURE atualizar_quantidade_colheita (operacao_id Operacao.id_operacao%TYPE, quantidade_nova Colheita.quantidade%TYPE);

  /* Lista de fatoes de produção: associa o seu id à respetiva quantidade */
  TYPE fatores_producao IS TABLE OF NUMBER INDEX BY BINARY_INTEGER;

  -- Procedimento para atualizar os fatores de produção de uma aplicação
  PROCEDURE atualizar_produtos_aplicacao_fp(operacao_id Operacao.id_operacao%TYPE, novos_fatores_producao fatores_producao);

  /* Exceção lançada quando existe uma restrição de aplicação de fatores de produção. */
  e_restricao_encontrada EXCEPTION;
END remarcar_operacoes;
