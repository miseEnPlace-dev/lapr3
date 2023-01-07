CREATE OR REPLACE PACKAGE BODY remarcar_operacoes AS
  -- Procedimento para cancelar uma operacao (colheita, rega ou aplicação de fatores de produção)
  -- Entende-se por cancelar uma operação deixar a data prevista nula e a data da operacao nula
  PROCEDURE cancelar_operacao (operacao_id Operacao.id_operacao%TYPE) IS
    data_prevista TIMESTAMP;
    data_op TIMESTAMP;
  BEGIN
    SELECT data_prevista_operacao, data_operacao
    INTO data_prevista, data_op
    FROM Operacao
    WHERE id_operacao = operacao_id;

    IF data_op IS NOT NULL THEN
      RAISE_APPLICATION_ERROR(-20001, 'Operação já realizada');
    ELSE IF data_prevista IS NOT NULL AND data_op IS NULL THEN
      UPDATE Operacao
      SET data_prevista_operacao = NULL
      WHERE id_operacao = operacao_id;
    END IF;
    END IF;
  END cancelar_operacao;

  -- Procedimento para atualizar a data de uma operação (colheita, rega ou aplicação de fatores de produção)
  PROCEDURE atualizar_data_prevista_operacao(operacao_id Operacao.id_operacao%TYPE, data_nova TIMESTAMP) IS
    data_prevista TIMESTAMP;
    data_op TIMESTAMP;
  BEGIN
    SELECT data_prevista_operacao, data_operacao
    INTO data_prevista, data_op
    FROM Operacao
    WHERE id_operacao = operacao_id;

    IF data_op IS NOT NULL THEN
      RAISE_APPLICATION_ERROR(-20001, 'Operação já realizada');
    ELSIF data_prevista IS NOT NULL AND data_op IS NULL THEN
      UPDATE Operacao
      SET data_prevista_operacao = data_nova
      WHERE id_operacao = operacao_id;
    ELSE
      RAISE_APPLICATION_ERROR(-20001, 'Operação não encontrada');
    END IF;
  END atualizar_data_prevista_operacao;

  -- Procedimento para atualizar a quantidade de uma colheita
  PROCEDURE atualizar_quantidade_colheita(operacao_id Operacao.id_operacao%TYPE, quantidade_nova Colheita.quantidade%TYPE) IS
    data_prevista TIMESTAMP;
    data_op TIMESTAMP;
  BEGIN
    SELECT data_prevista_operacao, data_operacao
    INTO data_prevista, data_op
    FROM Operacao
    WHERE id_operacao = operacao_id;

    IF data_op IS NOT NULL THEN
      RAISE_APPLICATION_ERROR(-20001, 'Operação já realizada');
    ELSE IF data_prevista IS NOT NULL AND data_op IS NULL THEN
        UPDATE Colheita
        SET quantidade = quantidade_nova
        WHERE id_operacao = operacao_id;
    END IF;
    END IF;
  END atualizar_quantidade_colheita;

  -- Procedimento para atualizar os fatores de produção de uma aplicação
  PROCEDURE atualizar_produtos_aplicacao_fp(operacao_id Operacao.id_operacao%TYPE, novos_fatores_producao fatores_producao) IS
    v_id_setor SETOR.id_setor%TYPE;
    v_id_fator_producao FATORPRODUCAO.id_fator_producao%TYPE;
    v_count NUMBER;
    v_data_prevista OPERACAO.data_prevista_operacao%TYPE;
  BEGIN
    SAVEPOINT inicio;

    BEGIN
      /** Verifica se a operação já foi realizada. */
      SELECT COUNT(*)
      INTO v_count
      FROM OPERACAO
      WHERE id_operacao = operacao_id
        AND data_operacao IS NULL;

      /** Verifica se a aplicação existe. */
      SELECT COUNT(*)
      INTO v_count
      FROM APLICACAO
      WHERE id_operacao = operacao_id;
    EXCEPTION
      WHEN NO_DATA_FOUND THEN
        RAISE_APPLICATION_ERROR(-20001, 'Operação não encontrada ou já realizada');
    END;

    /** Obtem o id do setor da operação. */
    SELECT id_setor
    INTO v_id_setor
    FROM APLICACAO
    WHERE id_operacao = operacao_id;

    /** Obtem a data prevista da operação. */
    SELECT data_prevista_operacao
    INTO v_data_prevista
    FROM OPERACAO
    WHERE id_operacao = operacao_id;

    /** Verifica se existe alguma restrição de aplicação de fatores de produção 
    para o setor, para o dia da operação. */
    v_id_fator_producao := novos_fatores_producao.FIRST;
    WHILE v_id_fator_producao IS NOT NULL LOOP
      SELECT COUNT(*)
      INTO v_count
      FROM RESTRICAOAPLICACAO RA
      WHERE RA.id_setor = v_id_setor
        AND RA.id_tipo_fator_producao = ( SELECT FP.id_tipo_fator_producao 
                                          FROM FATORPRODUCAO FP
                                          WHERE FP.id_fator_producao = v_id_fator_producao ) 
        AND RA.data_inicio <= v_data_prevista
        AND RA.data_fim >= v_data_prevista;

      IF v_count > 0 THEN
        RAISE e_restricao_encontrada;
      END IF;

      v_id_fator_producao := novos_fatores_producao.NEXT(v_id_fator_producao);
    END LOOP;

    /** Remove os fatores de produção atuais da aplicação. */
    DELETE FROM FATORPRODUCAOAPLICACAO
    WHERE id_operacao = operacao_id;

    /** Cria as associações entre a aplicação e os fatores de produção. */
    v_id_fator_producao := novos_fatores_producao.FIRST;
    WHILE v_id_fator_producao IS NOT NULL LOOP
      INSERT INTO FATORPRODUCAOAPLICACAO (id_operacao, id_fator_producao, quantidade)
      VALUES (operacao_id, v_id_fator_producao, novos_fatores_producao(v_id_fator_producao));

      v_id_fator_producao := novos_fatores_producao.NEXT(v_id_fator_producao);
    END LOOP;

    COMMIT;
  EXCEPTION
    WHEN e_restricao_encontrada THEN
      RAISE_APPLICATION_ERROR(-20001, 'Existe uma restrição de aplicação de fatores de produção para o setor e para o dia da operação.');
      ROLLBACK TO inicio;
    WHEN OTHERS THEN
      ROLLBACK TO inicio;
      RAISE;
  END atualizar_produtos_aplicacao_fp;
END remarcar_operacoes;
