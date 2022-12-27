CREATE OR REPLACE PACKAGE BODY gestao_operacoes AS
  /* Permite marcar uma aplicação de fatores de produção.
  *
  * Recebe a data prevista, o id do setor, o id do tipo de aplicação e a lista de fatores de produção.
  * Retorna o id da aplicação. */
  FUNCTION fn_registar_aplicacao_fp (
    data_prevista OPERACAO.data_prevista_operacao%TYPE,
    id_setor SETOR.id_setor%TYPE,
    id_tipo_aplicacao TIPOAPLICACAO.id_tipo_aplicacao%TYPE,
    lista_fatores_producao fatores_producao
  ) RETURN OPERACAO.id_operacao%TYPE IS
    id_operacao OPERACAO.id_operacao%TYPE;
    id_fator_producao FATORPRODUCAO.id_fator_producao%TYPE;
  BEGIN
    SAVEPOINT inicio;

    /** Verifica se existe alguma restrição de aplicação de fatores de produção 
    para o setor, para o dia da operação. */
