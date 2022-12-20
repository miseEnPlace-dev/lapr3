CREATE OR REPLACE PACKAGE BODY operacoes_nao_realizadas AS

  PROCEDURE cancel_operacao(id_operacao Operacao.id_operacao%TYPE) IS
  data_prevista Operacao.data_prevista%TYPE;
  data_realizada Operacao.data_realizada%TYPE;

  BEGIN

      SELECT data_prevista, data_realizada
      INTO data_prevista, data_realizada
      FROM Operacao
      WHERE id_operacao = id_operacao;

      IF data_prevista IS NOT NULL THEN
        IF data_realizada IS NULL THEN
          DELETE FROM Operacao
          WHERE id_operacao = id_operacao;
        ELSE
          RAISE_APPLICATION_ERROR(-20001, 'Operação já realizada');
        END IF;
      END IF;

  END cancel_operacao;

  PROCEDURE atualizar_operacao(id_operacao Operacao.id_operacao%TYPE) IS
  data_prevista Operacao.data_prevista%TYPE;
  data_realizada Operacao.data_realizada%TYPE;


END operacoes_nao_realizadas;
