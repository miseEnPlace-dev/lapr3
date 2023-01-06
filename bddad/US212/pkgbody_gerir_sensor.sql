CREATE OR REPLACE PACKAGE BODY gerir_sensores AS
  /** Permite obter o tuplo n da tabela InputSensor. */
  FUNCTION fn_obter_tuplo (
    p_n NUMBER
  ) RETURN inputsensor.string%TYPE 
  IS
    v_tuplo inputsensor.string%TYPE;
  BEGIN
    /** Obtem o tuplo n da tabela InputSensor. */
    SELECT string
    INTO v_tuplo
    FROM ( SELECT T.*, ROWNUM rnum
           FROM ( SELECT * 
                  FROM INPUTSENSOR ) T
           WHERE ROWNUM <= p_n )
    WHERE ROWNUM >= p_n;

    RETURN v_tuplo;
  END fn_obter_tuplo;

  /** Permite atualizar a tabela Sensor, a partir da tabela InputSensor. */
  PROCEDURE pr_atualizar_sensores IS
    v_id_leitura LEITURAINPUTSENSOR.id_leitura%TYPE;
    v_n_registos NUMBER;
    v_input_string inputsensor.string%TYPE;
    v_id_sensor SENSOR.id_sensor%TYPE;
    v_identificador SENSOR.identificador%TYPE;
    v_tipo TIPOSENSOR.tipo_sensor%TYPE;
    v_medicao MEDICAOSENSOR.medicao%TYPE;
    v_valor_referencia SENSOR.valor_referencia%TYPE;
    v_data MEDICAOSENSOR.data_hora%TYPE;
    v_data_string VARCHAR2(12);
    v_id_medicao MEDICAOSENSOR.id_medicao%TYPE;
  BEGIN
    SAVEPOINT inicio;

    /** Obtem o numero de registos da tabela InputSensor. */
    SELECT COUNT(*)
    INTO v_n_registos
    FROM INPUTSENSOR;

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
      v_input_string := fn_obter_tuplo(1);

      /** Apaga o registo da tabela InputSensor. */
      DELETE FROM INPUTSENSOR
      WHERE string LIKE v_input_string;

      /** Obtem os dados do registo obtido */
      v_identificador := SUBSTR(v_input_string, 1, 5);
      v_tipo := SUBSTR(v_input_string, 6, 2);
      v_medicao := SUBSTR(v_input_string, 8, 3);
      v_valor_referencia := SUBSTR(v_input_string, 11, 3);
      v_data_string := SUBSTR(v_input_string, 14, 12);

      /** Verifica se algum dos dados é inválido. */
      IF v_identificador IS NULL OR v_tipo IS NULL OR v_medicao IS NULL OR v_valor_referencia IS NULL OR v_data_string IS NULL THEN
        pr_regista_erro(v_id_leitura, v_identificador);
        CONTINUE;
      END IF;

      /** Converte a data para o formato TIMESTAMP, e verifica se é válida */
      BEGIN
        v_data := TO_TIMESTAMP(v_data_string, 'YYYYMMDDHH24MI');
      EXCEPTION
        WHEN OTHERS THEN
          pr_regista_erro(v_id_leitura, v_identificador);
          CONTINUE;
      END;

      BEGIN
        /** Verifica se o tipo do sensor é válido. */
        SELECT TS.tipo_sensor
        INTO v_tipo
        FROM TIPOSENSOR TS
        WHERE UPPER(TS.tipo_sensor) = UPPER(v_tipo);
      EXCEPTION 
        WHEN NO_DATA_FOUND THEN
          v_tipo := NULL;
      END;

      /** Se o tipo do sensor não for válido, regista o erro. */
      IF v_tipo IS NULL THEN
        pr_regista_erro(v_id_leitura, v_identificador);
        CONTINUE;
      END IF;

      BEGIN
        /** Verifica se o sensor existe na tabela Sensor. */
        SELECT id_sensor
        INTO v_id_sensor
        FROM sensor
        WHERE identificador = v_identificador;
      EXCEPTION 
        WHEN NO_DATA_FOUND THEN
          v_id_sensor := NULL;
      END;

      /** Se o sensor não existir, cria um novo */
      IF v_id_sensor IS NULL THEN
        /** Obtém o próximo valor da sequência id_sensor. */
        SELECT NVL(MAX(id_sensor), 0) + 1
        INTO v_id_sensor
        FROM SENSOR;

        /** Insere o registo na tabela Sensor. */
        INSERT INTO SENSOR (id_sensor, identificador, id_tipo_sensor, valor_referencia)
        VALUES (v_id_sensor, v_identificador, ( SELECT TS.id_tipo_sensor
                                                FROM TIPOSENSOR TS
                                                WHERE TS.tipo_sensor = v_tipo ), v_valor_referencia);
      END IF;

      /** Obtém o próximo valor da sequência id_medicao. */
      SELECT NVL(MAX(id_medicao), 0) + 1
      INTO v_id_medicao
      FROM MEDICAOSENSOR;

      /** Insere o registo na tabela MedicaoSensor. */
      INSERT INTO MEDICAOSENSOR (id_medicao, id_sensor, medicao, data_hora)
      VALUES (v_id_medicao, v_id_sensor, v_medicao, v_data);
    END LOOP;

    pr_imprime_informacao_leitura(v_id_leitura);

    COMMIT;
  END pr_atualizar_sensores;

  /** Permite registar um erro na tabela ErroInputSensor. 
  * Se o sensor não existir, é inserido um registo com o identificador do sensor a 0.
  */
  PROCEDURE pr_regista_erro (
    p_id_leitura LEITURAINPUTSENSOR.id_leitura%TYPE,
    p_identificador SENSOR.identificador%TYPE
  ) IS
    v_id_erro ErroLeituraInputSensor.id_erro_leitura%TYPE;
  BEGIN
    /** Obtém o próximo valor da sequência id_erro. */
    SELECT NVL(MAX(id_erro_leitura), 0) + 1
    INTO v_id_erro
    FROM ERROLEITURAINPUTSENSOR;

    /** Insere o erro na tavela ErroLeituraInputSensor. */
    INSERT INTO ERROLEITURAINPUTSENSOR (id_erro_leitura, id_leitura, identificador)
    VALUES (v_id_erro, p_id_leitura, p_identificador);
  END pr_regista_erro;

  PROCEDURE pr_imprime_informacao_leitura (
    p_id_leitura LEITURAINPUTSENSOR.id_leitura%TYPE
  ) IS
    v_data_hora TIMESTAMP;
    v_n_registos_lidos NUMBER;
    v_n_registos_erro NUMBER;
    v_n_registos_ok NUMBER;
  BEGIN
    /** Obtem o número de registos lidos na leitura e a data. */
    SELECT n_registos_lidos, data_hora
    INTO v_n_registos_lidos, v_data_hora
    FROM LEITURAINPUTSENSOR
    WHERE id_leitura = p_id_leitura;

    /** Obtem o número de registos com erro. */
    SELECT COUNT(*)
    INTO v_n_registos_erro
    FROM ERROLEITURAINPUTSENSOR
    WHERE id_leitura = p_id_leitura;

    /** Calcula o número de registos válidos. */
    v_n_registos_ok := v_n_registos_lidos - v_n_registos_erro;

    /** Imprime a informação da leitura. */
    DBMS_OUTPUT.PUT_LINE('Leitura ' || p_id_leitura || ' - ' || v_data_hora);
    DBMS_OUTPUT.PUT_LINE('Registos transferidos (válidos): ' || v_n_registos_ok);
    DBMS_OUTPUT.PUT_LINE('Registos não transferidos (inválidos): ' || v_n_registos_erro);
    DBMS_OUTPUT.PUT_LINE('Registos totais: ' || v_n_registos_lidos);
  END pr_imprime_informacao_leitura; 
END gerir_sensores;
