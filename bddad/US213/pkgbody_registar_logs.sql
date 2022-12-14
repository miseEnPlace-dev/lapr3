CREATE OR REPLACE PACKAGE BODY registar_logs AS
  
  PROCEDURE pr_RegistarInsert(utilizador LOGS.utilizador%TYPE,
    data_hora LOGS.data_alteracao%TYPE,
    tab LOGS.tabela%TYPE) AS
      new_id LOGS.id_log%TYPE;

    BEGIN
      SAVEPOINT inicio;


      /* check if there is any registered Logs */
      SELECT MAX(id_log) INTO new_id 
      FROM Logs;
      IF new_id IS NULL THEN
        new_id := 0;
      END IF;
      new_id := new_id + 1;


      INSERT INTO LOGS(id_log, utilizador, tipo_alteracao, data_alteracao, tabela)
      VAlUES (new_id, utilizador, 'INSERT', data_hora, tab);

  END pr_RegistarInsert;



  PROCEDURE pr_RegistarUpdate(utilizador LOGS.utilizador%TYPE,
    data_hora LOGS.data_alteracao%TYPE,
    tab LOGS.tabela%TYPE) AS
      new_id LOGS.id_log%TYPE;

    BEGIN
      SAVEPOINT inicio;


      /* check if there is any registered Logs */
      SELECT MAX(id_log) INTO new_id 
      FROM LOGS;
      IF new_id IS NULL THEN
        new_id := 0;
      END IF;
      new_id := new_id + 1;


      INSERT INTO LOGS(id_log, utilizador, tipo_alteracao, data_alteracao, tabela)
      VAlUES (new_id, utilizador, 'UPDATE', data_hora, tab);

  END pr_RegistarUpdate;

  
  PROCEDURE pr_RegistarDelete(utilizador LOGS.utilizador%TYPE,
    data_hora LOGS.data_alteracao%TYPE,
    tab LOGS.tabela%TYPE) AS
      new_id LOGS.id_log%TYPE;

    BEGIN
      SAVEPOINT inicio;


      /* check if there is any registered Logs */
      SELECT MAX(id_log) INTO new_id 
      FROM LOGS;
      IF new_id IS NULL THEN
        new_id := 0;
      END IF;
      new_id := new_id + 1;


      INSERT INTO LOGS(id_log, utilizador, tipo_alteracao, data_alteracao, tabela)
      VAlUES (new_id, utilizador, 'DELETE', data_hora, tab);

  END pr_RegistarDelete;



END registar_logs;
