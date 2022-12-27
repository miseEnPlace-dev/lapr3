CREATE OR REPLACE PACKAGE gestao_operacoes AS
  /* Lista de fatoes de produção: associa o seu id à respetiva quantidade */
  TYPE fatores_producao IS TABLE OF NUMBER INDEX BY BINARY_INTEGER;

  /* Permite marcar uma aplicação de fatores de produção.
  *
  * Recebe a data prevista, o id do setor, o id do tipo de aplicação e a lista de fatores de produção.
  * Retorna o id da aplicação. */
  FUNCTION fn_registar_aplicacao_fp (
    data_prevista OPERACAO.data_prevista_operacao%TYPE,
    id_setor SETOR.id_setor%TYPE,
    id_tipo_aplicacao TIPOAPLICACAO.id_tipo_aplicacao%TYPE,
    lista_fatores_producao fatores_producao
  ) RETURN OPERACAO.id_operacao%TYPE;

  /* Permite marcar uma rega.
  *
  * Recebe a data prevista, o id do setor e o id do tipo de rega.
  * Retorna o id da rega. */
  FUNCTION fn_registar_rega (
    data_prevista OPERACAO.data_prevista_operacao%TYPE,
    id_setor SETOR.id_setor%TYPE,
    id_tipo_rega TIPOREGA.id_tipo_rega%TYPE
  ) RETURN OPERACAO.id_operacao%TYPE;

  /* Permite marcar uma colheita.
  *
  * Recebe a data prevista, o id da plantação, o id do produto e respetiva quantidade.
  * Retorna o id da colheita. */
  FUNCTION fn_registar_colheita (
    data_prevista OPERACAO.data_prevista_operacao%TYPE,
    id_plantacao PLANTACAO.id_plantacao%TYPE,
    id_produto PRODUTO.id_produto%TYPE,
    quantidade_produto COLHEITA.quantidade%TYPE
  ) RETURN OPERACAO.id_operacao%TYPE;

  /* Verifica as restrições de uma aplicação de fatores de produção.
  *
  * Recebe o id da aplicação.
  * Retorna TRUE se a aplicação é válida, FALSE caso contrário. */
  FUNCTION fn_verificar_aplicacao_fp (
    id_operacao OPERACAO.id_operacao%TYPE
  ) RETURN BOOLEAN;

  /* Lista as restrições de aplicação de fatores de produção num dado setor, para um dado dia.
  *
  * Recebe o id do setor e a data.
  * Imprime a lista de restrições. */
  PROCEDURE pr_listar_restricoes_aplicacao_fp (
    id_setor SETOR.id_setor%TYPE,
    data DATE
  );

  /* Lista as operações de um dado setor, para um dado intervalo de tempo.
  *
  * Recebe o id do setor, a data inicial e a data final.
  * Imprime a lista de operações. */
  PROCEDURE pr_listar_operacoes (
    id_setor SETOR.id_setor%TYPE,
    data_inicial DATE,
    data_final DATE
  );

  /* Exceção lançada quando existe uma restrição de aplicação de fatores de produção. */
  restricao_encontrada EXCEPTION;
END gestao_operacoes;