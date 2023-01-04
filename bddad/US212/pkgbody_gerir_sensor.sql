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
    v_id_leitura LEITURAINPUTSENSOR.id_leitura%TYPE;
    v_n_registos NUMBER;
    v_input_string input_sensor.input_string%TYPE;
    v_id_sensor SENSOR.id_sensor%TYPE;
    v_identificador SENSOR.identificador%TYPE;
    v_tipo TIPOSENSOR.tipo_sensor%TYPE;
    v_medicao MEDICAOSENSOR.medicao%TYPE;
    v_valor_referencia SENSOR.valor_referencia%TYPE;
    v_data MEDICAOSENSOR.data_hora%TYPE;
  BEGIN
    SAVEPOINT inicio;

    /** Obtem o numero de registos da tabela InputSensor. */
    SELECT COUNT(*)
    INTO v_n_registos
    FROM input_sensor;

    /** Obtém o próximo valor da sequência id_leitura. */
    SELECT NVL(MAX(id_leitura), 0) + 1
    INTO v_id_leitura
    FROM LEITURAINPUTSENSOR;

    /** Insere o registo na tabela LeituraInputSensor. */
    INSERT INTO LEITURAINPUTSENSOR (id_leitura, n_registos_lidos, data_hora)
    VALUES (v_id_leitura, v_n_registos, SYSDATE);

    /** Percorre a tabela InputSensor */
    FOR i IN 1..v_n_registos LOOP
      /** Obtem o tuplo n da tabela InputSensor. */
      v_input_string := fn_obter_tuplo(i);

      /** Obtem os dados do registo obtido */
      v_identificador := SUBSTR(v_input_string, 1, 5);
      v_tipo := SUBSTR(v_input_string, 6, 2);
      v_medicao := SUBSTR(v_input_string, 8, 3);
      v_valor_referencia := SUBSTR(v_input_string, 11, 3);
      v_data := SUBSTR(v_input_string, 14, 12);

      /** Verifica se o sensor existe na tabela Sensor. */
      SELECT id_sensor
      INTO v_id_sensor
      FROM sensor
      WHERE identificador = v_identificador;

      /** Se o sensor não existir, lança a exceção sensor_inexistente. */
      IF v_id_sensor IS NULL THEN
        RAISE sensor_inexistente;
      END IF;

      /** Verifica se o tipo do sensor é válido. */



    END LOOP;


END gerir_sensores;
