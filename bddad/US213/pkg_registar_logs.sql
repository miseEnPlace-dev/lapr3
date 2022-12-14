CREATE OR REPLACE PACKAGE registar_logs AS

  PROCEDURE pr_RegistarInsert(utilizador LOGS.utilizador%TYPE,
    data_hora LOGS.data_alteracao%TYPE,
    tab LOGS.tabela%TYPE);

  PROCEDURE pr_RegistarDelete(utilizador LOGS.utilizador%TYPE,
    data_hora LOGS.data_alteracao%TYPE,
    tab LOGS.tabela%TYPE);

  PROCEDURE pr_RegistarUpdate(utilizador LOGS.utilizador%TYPE,
    data_hora LOGS.data_alteracao%TYPE,
    tab LOGS.tabela%TYPE);

END registar_logs;