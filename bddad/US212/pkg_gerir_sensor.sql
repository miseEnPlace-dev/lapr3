CREATE OR REPLACE PACKAGE gerir_sensores AS
  /** Permite obter o tuplo n da tabela InputSensor. */
  FUNCTION fn_obter_tuplo (
    p_n NUMBER
  ) RETURN input_sensor.input_string%TYPE;

  /** Permite atualizar a tabela Sensor, a partir da tabela InputSensor. */
  PROCEDURE pr_atualizar_sensores;
END gerir_sensores;
