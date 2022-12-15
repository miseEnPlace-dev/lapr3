CREATE OR REPLACE PACKAGE BODY operacoes_nao_realizadas AS

  PROCEDURE cancel_operacao IS
    CURSOR all_operacoes IS
      SELECT id_operacao, data_inicio, data_fim, valor, estado, sensor
      FROM OPERACOES
      WHERE estado = 'Nao realizada'
      ORDER BY data_inicio;
    operacao_id OPERACOES.id_operacao%TYPE;
    data_inicio OPERACOES.data_inicio%TYPE;
    data_fim OPERACOES.data_fim%TYPE;
    estado OPERACOES.estado%TYPE;
    valor OPERACOES.valor%TYPE;
    sensor OPERACOES.sensor%TYPE;

  BEGIN

    OPEN all_operacoes;
    FETCH all_operacoes INTO operacao_id, data_inicio, data_fim, valor, estado, sensor;

    IF all_operacoes%ROWCOUNT = 0 THEN
      DBMS_OUTPUT.PUT_LINE('Não existem operações não realizadas.');
    ELSE
      DBMS_OUTPUT.PUT_LINE('Operações não realizadas:');
      DBMS_OUTPUT.PUT_LINE('');

      LOOP
        EXIT WHEN all_operacoes%NOTFOUND;

        DBMS_OUTPUT.PUT_LINE('----');
        DBMS_OUTPUT.PUT_LINE('Operação id: ' || operacao_id  ' entre ' || TO_CHAR(data_inicio, 'DD/MM/YYYY') || ' e ' || TO_CHAR(data_fim, 'DD/MM/YYYY') || ' com o valor ' || valor || ' e o estado ' || estado || '.');

        FETCH all_operacoes INTO operacao_id, data_inicio, data_fim, valor, estado, sensor;
      END LOOP;
    END IF;

    CLOSE all_operacoes;

  END cancel_operacao;
