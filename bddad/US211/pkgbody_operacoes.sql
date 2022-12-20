CREATE OR REPLACE PACKAGE BODY operacoes_nao_realizadas AS

  PROCEDURE cancel_operacao(operacao_id Operacao.id_operacao%TYPE) IS
  data_prevista_op Operacao.data_prevista_operacao%TYPE;
  data_op Operacao.data_operacao%TYPE;

  BEGIN

  IF(EXISTS(
      SELECT *
      FROM Operacao
      WHERE id_operacao = operacao_id
      AND data_prevista_operacao IS NOT NULL AND data_operacao IS NULL;
  )) THEN
    UPDATE Operacao
    SET data_prevista_operacao = NULL
    WHERE id_operacao = operacao_id;
  ELSE
    RAISE_APPLICATION_ERROR(-20001, 'Operação já realizada');
  END IF;

  END cancel_operacao;

END operacoes_nao_realizadas;
