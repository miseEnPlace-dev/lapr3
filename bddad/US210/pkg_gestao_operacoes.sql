CREATE OR REPLACE PACKAGE gestao_operacoes AS
  /* Lista de fatoes de produção: associa o seu id à respetiva quantidade */
  TYPE fatores_producao IS TABLE OF NUMBER INDEX BY BINARY_INTEGER;

  /* Permite marcar uma aplicação de fatores de produção.
  *
  * Recebe a data prevista, o id do setor, o id do tipo de aplicação e a lista de fatores de produção.
  * Retorna o id da aplicação. */
  FUNCTION fn_registar_aplicacao_fp (
    p_data_prevista OPERACAO.data_prevista_operacao%TYPE,
    p_id_setor SETOR.id_setor%TYPE,
    p_id_tipo_aplicacao TIPOAPLICACAO.id_tipo_aplicacao%TYPE,
    p_lista_fatores_producao fatores_producao
  ) RETURN OPERACAO.id_operacao%TYPE;

  /* Permite marcar uma rega.
  *
  * Recebe a data prevista, o id do setor e o id do tipo de rega.
  * Retorna o id da rega. */
  FUNCTION fn_registar_rega (
    p_data_prevista OPERACAO.data_prevista_operacao%TYPE,
    p_id_setor SETOR.id_setor%TYPE,
    p_id_tipo_rega TIPOREGA.id_tipo_rega%TYPE
  ) RETURN OPERACAO.id_operacao%TYPE;

  /* Permite marcar uma colheita.
  *
  * Recebe a data prevista, o id da plantação, o id do produto e respetiva quantidade.
  * Retorna o id da colheita. */
  FUNCTION fn_registar_colheita (
    p_data_prevista OPERACAO.data_prevista_operacao%TYPE,
    p_id_plantacao PLANTACAO.id_plantacao%TYPE,
    p_id_produto PRODUTO.id_produto%TYPE,
    p_quantidade_produto COLHEITA.quantidade%TYPE
  ) RETURN OPERACAO.id_operacao%TYPE;

  /* Verifica as restrições de uma aplicação de fatores de produção.
  *
  * Recebe o id da aplicação.
  * Retorna TRUE se a aplicação é válida, FALSE caso contrário. */
  FUNCTION fn_verificar_aplicacao_fp (
    p_id_operacao OPERACAO.id_operacao%TYPE
  ) RETURN BOOLEAN;

  /* Lista as restrições de aplicação de fatores de produção num dado setor, para um dado dia.
  *
  * Recebe o id do setor e a data.
  * Imprime a lista de restrições. */
  PROCEDURE pr_listar_restricoes_aplicacao_fp (
    p_id_setor SETOR.id_setor%TYPE,
    p_data DATE
  );

  /* Lista as operações de um dado setor, para um dado intervalo de tempo.
  *
  * Recebe o id do setor, a data inicial e a data final.
  * Imprime a lista de operações. */
  PROCEDURE pr_listar_operacoes (
    p_id_setor SETOR.id_setor%TYPE,
    p_data_inicial DATE,
    p_data_final DATE
  );

  /* Exceção lançada quando existe uma restrição de aplicação de fatores de produção. */
  e_restricao_encontrada EXCEPTION;
END gestao_operacoes;