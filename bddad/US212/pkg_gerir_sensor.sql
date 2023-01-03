CREATE OR REPLACE PACKAGE gerir_sensores AS
  FUNCTION fn_NEsimoTuplo(n NUMBER) RETURN input_sensor.input_string%TYPE;
  PROCEDURE pr_TransferirLeituras;
END gerir_sensores;
