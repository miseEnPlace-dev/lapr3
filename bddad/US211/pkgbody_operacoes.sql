CREATE OR REPLACE PACKAGE BODY operacoes AS

  -- Procedimento para cancelar operacao deixando a data prevista nula e a data da operacao nula
  PROCEDURE cancel_operacao (operacao_id Operacao.id_operacao%TYPE) IS
  data_prevista TIMESTAMP;
  data_op TIMESTAMP;
  BEGIN

    SELECT data_prevista_operacao, data_operacao
    INTO data_prevista, data_op
    FROM Operacao
    WHERE id_operacao = operacao_id;

    IF data_prevista IS NOT NULL AND data_op IS NOT NULL THEN
      RAISE_APPLICATION_ERROR(-20001, 'Operação já realizada');
    ELSE IF data_prevista IS NOT NULL AND data_op IS NULL THEN
      UPDATE Operacao
      SET data_prevista_operacao = NULL
      WHERE id_operacao = operacao_id;
    END IF;
    END IF;

    DBMS_OUTPUT.PUT_LINE('Operação cancelada com sucesso');

  END cancel_operacao;

  -- Procedimento para atualizar operacoes com datas deixando a data prevista com a data nova e a data da operacao nula
  PROCEDURE atualizar_operacao_datas(operacao_id Operacao.id_operacao%TYPE, data_nova TIMESTAMP) IS
  data_prevista TIMESTAMP;
  data_op TIMESTAMP;
  BEGIN
  SELECT data_prevista_operacao, data_operacao
    INTO data_prevista, data_op
    FROM Operacao
    WHERE id_operacao = operacao_id;

    IF data_prevista IS NOT NULL AND data_op IS NOT NULL THEN
      RAISE_APPLICATION_ERROR(-20001, 'Operação já realizada');
    ELSIF data_prevista IS NOT NULL AND data_op IS NULL THEN
      UPDATE Operacao
      SET data_prevista_operacao = data_nova
      WHERE id_operacao = operacao_id;
    ELSE
      RAISE_APPLICATION_ERROR(-20001, 'Operação não encontrada');
    END IF;

    DBMS_OUTPUT.PUT_LINE('Operação atualizada com sucesso');

  END atualizar_operacao_datas;

  -- Procedimento para atualizar operacoes com quantidade de produtos, ou seja, atualizar a quantidade de produtos na colheita de determinado produto em determinada operacao
  PROCEDURE atualizar_operacao_produtos(operacao_id Operacao.id_operacao%TYPE, quantidade_nova Colheita.quantidade%TYPE) IS
  data_prevista TIMESTAMP;
  data_op TIMESTAMP;
  BEGIN
  SELECT data_prevista_operacao, data_operacao
    INTO data_prevista, data_op
    FROM Operacao
    WHERE id_operacao = operacao_id;

    IF data_prevista IS NOT NULL AND data_op IS NOT NULL THEN
      RAISE_APPLICATION_ERROR(-20001, 'Operação já realizada');
    ELSE IF data_prevista IS NOT NULL AND data_op IS NULL THEN
        UPDATE Colheita
        SET quantidade = quantidade_nova
        WHERE id_operacao = operacao_id;
    END IF;
    END IF;

    DBMS_OUTPUT.PUT_LINE('Operação atualizada com sucesso');

  END atualizar_operacao_produtos;

END operacoes;
