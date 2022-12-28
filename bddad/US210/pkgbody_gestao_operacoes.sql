CREATE OR REPLACE PACKAGE BODY gestao_operacoes AS
  /* Permite marcar uma aplicação de fatores de produção.
  *
  * Recebe a data prevista, o id do setor, o id do tipo de aplicação e a lista de fatores de produção.
  * Retorna o id da aplicação. */
  FUNCTION fn_registar_aplicacao_fp (
    p_data_prevista OPERACAO.data_prevista_operacao%TYPE,
    p_id_setor SETOR.id_setor%TYPE,
    p_id_tipo_aplicacao TIPOAPLICACAO.id_tipo_aplicacao%TYPE,
    p_lista_fatores_producao fatores_producao
  ) RETURN OPERACAO.id_operacao%TYPE IS
    v_id_operacao OPERACAO.id_operacao%TYPE;
    v_id_fator_producao FATORPRODUCAO.id_fator_producao%TYPE;
    v_count NUMBER;
  BEGIN
    SAVEPOINT inicio;

    /** Verifica se existe alguma restrição de aplicação de fatores de produção 
    para o setor, para o dia da operação. */
    v_id_fator_producao := p_lista_fatores_producao.FIRST;
    WHILE v_id_fator_producao IS NOT NULL LOOP
      SELECT COUNT(*)
      INTO v_count
      FROM RESTRICAOAPLICACAO RA
      WHERE RA.id_setor = p_id_setor
        AND RA.id_tipo_fator_producao = ( SELECT FP.id_tipo_fator_producao 
                                          FROM FATORPRODUCAO FP
                                          WHERE FP.id_fator_producao = v_id_fator_producao ) 
        AND RA.data_inicio <= p_data_prevista
        AND RA.data_fim >= p_data_prevista;

      IF v_count > 0 THEN
        RAISE e_restricao_encontrada;
      END IF;

      v_id_fator_producao := p_lista_fatores_producao.NEXT(v_id_fator_producao);
    END LOOP;

    /** Encontra o próximo id de operação. */
    SELECT NVL(MAX(id_operacao), 0) + 1
    INTO v_id_operacao
    FROM OPERACAO;

    /** Cria a operação. */
    INSERT INTO OPERACAO (id_operacao, data_prevista_operacao)
    VALUES (v_id_operacao, p_data_prevista);

    /** Cria a aplicação de fatores de produção. */	
    INSERT INTO APLICACAO (id_operacao, id_setor, id_tipo_aplicacao)
    VALUES (v_id_operacao, p_id_setor, p_id_tipo_aplicacao);

    /** Cria as associações entre a aplicação e os fatores de produção. */
    v_id_fator_producao := p_lista_fatores_producao.FIRST;
    WHILE v_id_fator_producao IS NOT NULL LOOP
      INSERT INTO FATORPRODUCAOAPLICACAO (id_operacao, id_fator_producao, quantidade)
      VALUES (v_id_operacao, v_id_fator_producao, p_lista_fatores_producao(v_id_fator_producao));

      v_id_fator_producao := p_lista_fatores_producao.NEXT(v_id_fator_producao);
    END LOOP;

    COMMIT;
    RETURN v_id_operacao;
  EXCEPTION
    WHEN e_restricao_encontrada THEN
      RAISE_APPLICATION_ERROR(-20001, 'Existe uma restrição de aplicação de fatores de produção para o setor e para o dia da operação.');
      ROLLBACK TO inicio;
    WHEN OTHERS THEN
      ROLLBACK TO inicio;
      RAISE;
  END fn_registar_aplicacao_fp;

  /* Permite marcar uma rega.
  *
  * Recebe a data prevista, o id do setor e o id do tipo de rega.
  * Retorna o id da rega. */
  FUNCTION fn_registar_rega (
    p_data_prevista OPERACAO.data_prevista_operacao%TYPE,
    p_id_setor SETOR.id_setor%TYPE,
    p_id_tipo_rega TIPOREGA.id_tipo_rega%TYPE
  ) RETURN OPERACAO.id_operacao%TYPE IS
    v_id_operacao OPERACAO.id_operacao%TYPE;
  BEGIN
    SAVEPOINT inicio;

    /** Encontra o próximo id de operação. */
    SELECT NVL(MAX(id_operacao), 0) + 1
    INTO v_id_operacao
    FROM OPERACAO;

    /** Cria a operação. */
    INSERT INTO OPERACAO (id_operacao, data_prevista_operacao)
    VALUES (v_id_operacao, p_data_prevista);

    /** Cria a rega. */
    INSERT INTO REGA (id_operacao, id_setor, id_tipo_rega)
    VALUES (v_id_operacao, p_id_setor, p_id_tipo_rega);

    COMMIT;
    RETURN v_id_operacao;
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK TO inicio;
      RAISE;
  END fn_registar_rega;

  /* Permite marcar uma colheita.
  *
  * Recebe a data prevista, o id da plantação, o id do produto e respetiva quantidade.
  * Retorna o id da colheita. */
  FUNCTION fn_registar_colheita (
    p_data_prevista OPERACAO.data_prevista_operacao%TYPE,
    p_id_plantacao PLANTACAO.id_plantacao%TYPE,
    p_id_produto PRODUTO.id_produto%TYPE,
    p_quantidade_produto COLHEITA.quantidade%TYPE
  ) RETURN OPERACAO.id_operacao%TYPE IS
    v_id_operacao OPERACAO.id_operacao%TYPE;
  BEGIN
    SAVEPOINT inicio;

    /** Encontra o próximo id de operação. */
    SELECT NVL(MAX(id_operacao), 0) + 1
    INTO v_id_operacao
    FROM OPERACAO;

    /** Cria a operação. */
    INSERT INTO OPERACAO (id_operacao, data_prevista_operacao)
    VALUES (v_id_operacao, p_data_prevista);

    /** Cria a colheita. */
    INSERT INTO COLHEITA (id_operacao, id_plantacao, id_produto, quantidade)
    VALUES (v_id_operacao, p_id_plantacao, p_id_produto, p_quantidade_produto);

    COMMIT;
    RETURN v_id_operacao;
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK TO inicio;
      RAISE;
  END fn_registar_colheita;

  /* Verifica as restrições de uma aplicação de fatores de produção.
  *
  * Recebe o id da aplicação.
  * Retorna TRUE se a aplicação é válida, FALSE caso contrário. */
  FUNCTION fn_verificar_aplicacao_fp (
    p_id_operacao OPERACAO.id_operacao%TYPE
  ) RETURN BOOLEAN IS
    /** Cursor que contém os tipos de fatores de produção associados à aplicação. */
    CURSOR c_id_tipos_fatores_producao IS
      SELECT TP.id_tipo_fator_producao
      FROM TIPOFATORPRODUCAO TP
      INNER JOIN FATORPRODUCAO FP
      ON TP.id_tipo_fator_producao = FP.id_tipo_fator_producao
      INNER JOIN FATORPRODUCAOAPLICACAO FPA
      ON FP.id_fator_producao = FPA.id_fator_producao
      WHERE FPA.id_operacao = p_id_operacao;
    v_id_tipo_fator_producao TIPOFATORPRODUCAO.id_tipo_fator_producao%TYPE;
    v_count NUMBER;
  BEGIN
    /** Para cada tipo de fator de produção associado à aplicação, 
    verifica se existe uma restrição para o setor e para o dia da operação. */
    FOR v_id_fator_producao IN c_id_tipos_fatores_producao LOOP
      SELECT COUNT(*)
      INTO v_count
      FROM RESTRICAOAPLICACAO RA
      WHERE RA.id_tipo_fator_producao = v_id_tipo_fator_producao
      AND RA.id_setor = ( SELECT id_setor 
                          FROM APLICACAO A
                          WHERE A.id_operacao = p_id_operacao )
      AND RA.data_inicio <= ( SELECT data_prevista_operacao
                              FROM OPERACAO O
                              WHERE O.id_operacao = p_id_operacao )
      AND RA.data_fim >= ( SELECT data_prevista_operacao
                           FROM OPERACAO O
                           WHERE O.id_operacao = p_id_operacao );

      IF v_count > 0 THEN
        RETURN FALSE;
      END IF;
    END LOOP;

    RETURN TRUE;
  END fn_verificar_aplicacao_fp;

  /* Lista as restrições de aplicação de fatores de produção num dado setor, para um dado dia.
  *
  * Recebe o id do setor e a data.
  * Imprime a lista de restrições. */
  PROCEDURE pr_listar_restricoes_aplicacao_fp (
    p_id_setor SETOR.id_setor%TYPE,
    p_data DATE
  ) IS
    /** Cursor que contém as restrições de aplicação de fatores de produção. */
    CURSOR c_restricoes_aplicacao_fp IS
      SELECT RA.id_tipo_fator_producao, TFP.tipo_fator_producao, RA.data_inicio, RA.data_fim
      FROM RESTRICAOAPLICACAO RA
      INNER JOIN TIPOFATORPRODUCAO TFP
      ON RA.id_tipo_fator_producao = TFP.id_tipo_fator_producao
      WHERE RA.id_setor = p_id_setor
      AND RA.data_inicio <= p_data
      AND RA.data_fim >= p_data;
  BEGIN
    DBMS_OUTPUT.PUT_LINE('Restrições de aplicação de fatores de produção no setor ' || p_id_setor || ' para o dia ' || p_data || ':');

    /** Para cada restrição, imprime os seus dados. */
    FOR v_restricao_aplicacao_fp IN c_restricoes_aplicacao_fp LOOP
      DBMS_OUTPUT.PUT_LINE(chr(13)||chr(10));
      DBMS_OUTPUT.PUT_LINE('Tipo de fator de produção: ' || v_restricao_aplicacao_fp.tipo_fator_producao || ' (' || v_restricao_aplicacao_fp.id_tipo_fator_producao || ')');
      DBMS_OUTPUT.PUT_LINE('Data de início: ' || v_restricao_aplicacao_fp.data_inicio);
      DBMS_OUTPUT.PUT_LINE('Data de fim: ' || v_restricao_aplicacao_fp.data_fim);
    END LOOP;
  END pr_listar_restricoes_aplicacao_fp;

  /* Lista as operações de um dado setor, para um dado intervalo de tempo.
  *
  * Recebe o id do setor, a data inicial e a data final.
  * Imprime a lista de operações. */
  PROCEDURE pr_listar_operacoes (
    p_id_setor SETOR.id_setor%TYPE,
    p_data_inicial DATE,
    p_data_final DATE
  ) IS
    /** Cursor que contém as operações de rega. */
    CURSOR c_regas IS
      SELECT R.id_operacao, O.data_prevista_operacao, O.data_operacao
      FROM REGA R
      INNER JOIN OPERACAO O
      ON R.id_operacao = O.id_operacao
      WHERE R.id_setor = p_id_setor
      AND O.data_prevista_operacao >= p_data_inicial
      AND O.data_prevista_operacao <= p_data_final;
    /** Cursor que contém as operações de aplicação de fatores de produção. */
    CURSOR c_aplicacoes IS
      SELECT A.id_operacao, O.data_prevista_operacao, O.data_operacao
      FROM APLICACAO A
      INNER JOIN OPERACAO O
      ON A.id_operacao = O.id_operacao
      WHERE A.id_setor = p_id_setor
      AND O.data_prevista_operacao >= p_data_inicial
      AND O.data_prevista_operacao <= p_data_final;
    /** Cursor que contém as operações de colheita. */
    CURSOR c_colheitas IS
      SELECT C.id_operacao, C.id_produto, C.quantidade, O.data_prevista_operacao, O.data_operacao
      FROM COLHEITA C
      INNER JOIN PLANTACAO P
      ON C.id_plantacao = P.id_plantacao
      INNER JOIN OPERACAO O
      ON C.id_operacao = O.id_operacao
      WHERE P.id_setor = p_id_setor
      AND O.data_prevista_operacao >= p_data_inicial
      AND O.data_prevista_operacao <= p_data_final;
  BEGIN
    DBMS_OUTPUT.PUT_LINE('Operações no setor ' || p_id_setor || ' entre ' || p_data_inicial || ' e ' || p_data_final || ':');

    /** Para cada operação de rega, imprime os seus dados. */
    FOR v_rega IN c_regas LOOP
      DBMS_OUTPUT.PUT_LINE(chr(13)||chr(10));
      DBMS_OUTPUT.PUT_LINE('ID da operação: ' || v_rega.id_operacao);
      DBMS_OUTPUT.PUT_LINE('Data prevista: ' || v_rega.data_prevista_operacao);
      DBMS_OUTPUT.PUT_LINE('Data de realização: ' || v_rega.data_operacao);
      DBMS_OUTPUT.PUT_LINE('Tipo de operação: Rega');
    END LOOP;

    /** Para cada operação de aplicação de fatores de produção, imprime os seus dados. */
    FOR v_aplicacao IN c_aplicacoes LOOP
      DBMS_OUTPUT.PUT_LINE(chr(13)||chr(10));
      DBMS_OUTPUT.PUT_LINE('ID da operação: ' || v_aplicacao.id_operacao);
      DBMS_OUTPUT.PUT_LINE('Data prevista: ' || v_aplicacao.data_prevista_operacao);
      DBMS_OUTPUT.PUT_LINE('Data de realização: ' || v_aplicacao.data_operacao);
      DBMS_OUTPUT.PUT_LINE('Tipo de operação: Aplicação de fatores de produção');
    END LOOP;

    /** Para cada operação de colheita, imprime os seus dados. */
    FOR v_colheita IN c_colheitas LOOP
      DBMS_OUTPUT.PUT_LINE(chr(13)||chr(10));
      DBMS_OUTPUT.PUT_LINE('ID da operação: ' || v_colheita.id_operacao);
      DBMS_OUTPUT.PUT_LINE('Data prevista: ' || v_colheita.data_prevista_operacao);
      DBMS_OUTPUT.PUT_LINE('Data de realização: ' || v_colheita.data_operacao);
      DBMS_OUTPUT.PUT_LINE('ID do produto: ' || v_colheita.id_produto);
      DBMS_OUTPUT.PUT_LINE('Quantidade: ' || v_colheita.quantidade);
      DBMS_OUTPUT.PUT_LINE('Tipo de operação: Colheita');
    END LOOP;
  END;
END gestao_operacoes;