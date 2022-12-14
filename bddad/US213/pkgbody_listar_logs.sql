CREATE OR REPLACE PACKAGE BODY listar_logs AS

  PROCEDURE pr_ListarLogs IS
    CURSOR all_logs IS
      SELECT id_log, utilizador, tipo_alteracao, data_alteracao, tabela
      FROM LOGS
      ORDER BY data_alteracao;
    log_id LOGS.id_log%TYPE;
    utilizador LOGS.utilizador%TYPE;
    alteracao LOGS.tipo_alteracao%TYPE;
    data_hora LOGS.data_alteracao%TYPE;
    tab LOGS.tabela%TYPE;
  BEGIN
    OPEN all_logs;
    FETCH all_logs INTO log_id, utilizador, alteracao, data_hora, tab;

    IF all_logs%ROWCOUNT = 0 THEN
      DBMS_OUTPUT.PUT_LINE('Não existem logs registadas.');
    ELSE
      DBMS_OUTPUT.PUT_LINE('Logs:');
      DBMS_OUTPUT.PUT_LINE('');

      LOOP
        EXIT WHEN all_logs%NOTFOUND;

        DBMS_OUTPUT.PUT_LINE('----');
        DBMS_OUTPUT.PUT_LINE('Log id: ' || log_id || '. Utilizador ' || utilizador || ' realizou ' || alteracao || ' na tabela ' || tab || ' a ' || TO_CHAR(data_hora, 'DD/MM/YYYY') || '.');

        FETCH all_logs INTO log_id, utilizador, alteracao, data_hora, tab;
      END LOOP;
    END IF;

    CLOSE all_logs;
  END pr_ListarLogs;

END listar_logs;
