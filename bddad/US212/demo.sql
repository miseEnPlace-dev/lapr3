DECLARE
  n input_sensor.input_string%TYPE;
BEGIN
  n := gerir_sensores.fn_NEsimoTuplo(1);
  DBMS_OUTPUT.PUT_LINE(n);
END;
