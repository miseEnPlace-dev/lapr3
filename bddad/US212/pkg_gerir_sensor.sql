CREATE OR REPLACE PACKAGE gerir_sensores AS
  /** A input_string da tabela InputSensor tem o seguinte formato:
  *  IIIIIHHMMMVVVYYYYMMDDHHMM
  *
  *  identificador: 5 caracteres (IIIII)
  *  tipo: 2 caracteres (TT)
  *  medicao: 3 caracteres (MMM)
  *  valor_referencia: 3 caracteres (VVV)
  *  data_hora: 12 caracteres (YYYYMMDDHHMM)
  *
  *  Exemplo: 12345HS070090202301051544
  */

  /** Permite obter o tuplo n da tabela InputSensor. */
  FUNCTION fn_obter_tuplo (
    p_n NUMBER
  ) RETURN INPUTSENSOR.string%TYPE;

  /** Permite atualizar a tabela Sensor, a partir da tabela InputSensor. */
  PROCEDURE pr_atualizar_sensores;

  /** Permite registar um erro na tabela ErroInputSensor. 
  * Se o sensor não existir, é inserido um registo com o identificador do sensor a 0.
  */
  PROCEDURE pr_regista_erro (
    p_id_leitura LEITURAINPUTSENSOR.id_leitura%TYPE,
    p_identificador SENSOR.identificador%TYPE
  );

  PROCEDURE pr_imprime_informacao_leitura (
    p_id_leitura LEITURAINPUTSENSOR.id_leitura%TYPE
  );
END gerir_sensores;
