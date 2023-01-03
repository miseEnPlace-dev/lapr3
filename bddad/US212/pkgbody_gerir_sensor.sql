CREATE OR REPLACE PACKAGE BODY gerir_sensores AS
  /** Permite obter o tuplo n da tabela InputSensor. */
  FUNCTION fn_obter_tuplo (
    p_n NUMBER
  ) RETURN input_sensor.input_string%TYPE 
  IS
    v_tuplo input_sensor.input_string%TYPE;
  BEGIN
    /** Obtem o tuplo n da tabela InputSensor. */
    SELECT *
    INTO v_tuplo
    FROM ( SELECT T.*, ROWNUM rnum
           FROM ( SELECT * 
                  FROM CLIENTE ) T
           WHERE ROWNUM <= p_n )
    WHERE ROWNUM >= p_n;

    RETURN v_tuplo;
  END fn_obter_tuplo;

  /** Permite atualizar a tabela Sensor, a partir da tabela InputSensor. */
  PROCEDURE pr_atualizar_sensores IS
     
END gerir_sensores;
