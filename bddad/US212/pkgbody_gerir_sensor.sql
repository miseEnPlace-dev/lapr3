CREATE OR REPLACE PACKAGE BODY gerir_sensores AS
  FUNCTION fn_NEsimoTuplo(n NUMBER) RETURN input_sensor.input_string%TYPE AS
    t input_sensor.input_string%TYPE;
  BEGIN
    SELECT input_string INTO t FROM input_sensor WHERE rownum = n;
    RETURN t;
  END fn_NEsimoTuplo;
END gerir_sensores;
