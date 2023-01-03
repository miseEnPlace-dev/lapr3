CREATE OR REPLACE PACKAGE BODY gestao_hubs AS
  /* Permite atualizar a tabela Hub, a partir da tabela InputHub. */
  PROCEDURE pr_atualizar_hubs IS
    /** Cursor que contém os dados da tabela InputHub. */
    CURSOR c_strings IS
      SELECT string
      FROM INPUTHUB;
    TYPE v_array IS VARRAY(4) OF VARCHAR2(20);
    v_columns v_array;
    v_id_hub HUB.id_hub%TYPE;
    v_string VARCHAR2(100);
    v_first_char VARCHAR2(1);
  BEGIN
    SAVEPOINT inicio;

    /** Encontra o próximo id da tabela Hub. */
    SELECT NVL(MAX(id_hub), 0) + 1
    INTO v_id_hub
    FROM HUB;

    /** Percorre todos os registros da tabela InputHub. */
    FOR v_string IN c_strings LOOP
      /** Faz o split da string. */
      SELECT REGEXP_SUBSTR(v_string.string, '[^;]+', 1, LEVEL)
      BULK COLLECT INTO v_columns
      FROM dual
      CONNECT BY REGEXP_SUBSTR(v_string.string, '[^;]+', 1, LEVEL) IS NOT NULL;

      /** Verifica se o registo é um Hub. */
      SELECT SUBSTR(v_columns(4), 1, 1)
      INTO v_first_char
      FROM dual;

      IF (v_first_char = 'C' OR v_first_char = 'E') THEN
        /** Insere o registo na tabela Hub. */
        INSERT INTO HUB (id_hub, codigo_hub, designacao, latitude, longitude)
        VALUES (v_id_hub, v_columns(1), v_columns(1), v_columns(2), v_columns(3));

        /** Incrementa o id do Hub. */
        v_id_hub := v_id_hub + 1;
      END IF;
    END LOOP;
  
    COMMIT;
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK TO inicio;
      RAISE;
  END pr_atualizar_hubs;

  /* Permite definir/atualizar o Hub de entrega de um cliente.
  *
  * Recebe o id do cliente e o id do Hub de entrega. */
  PROCEDURE pr_definir_hub_entrega (
    p_id_cliente CLIENTE.id_cliente%TYPE,
    p_id_hub_entrega HUB.id_hub%TYPE
  ) IS
    v_count NUMBER;
  BEGIN
    SAVEPOINT inicio;

    /** Verifica se o cliente existe. */
    SELECT COUNT(*)
    INTO v_count
    FROM CLIENTE
    WHERE id_cliente = p_id_cliente;

    IF (v_count = 0) THEN
      RAISE e_cliente_inexistente;
    END IF;

    /** Verifica se o hub existe. */
    SELECT COUNT(*)
    INTO v_count
    FROM HUB
    WHERE id_hub = p_id_hub_entrega;

    IF (v_count = 0) THEN
      RAISE e_hub_inexistente;
    END IF;

    /** Atualiza o hub de entrega do cliente. */
    UPDATE CLIENTE
    SET id_hub_entrega = p_id_hub_entrega
    WHERE id_cliente = p_id_cliente;

    COMMIT;
  EXCEPTION
    WHEN e_cliente_inexistente THEN
      ROLLBACK TO inicio;
      RAISE_APPLICATION_ERROR(-20001, 'Cliente inexistente.');
    WHEN e_hub_inexistente THEN
      ROLLBACK TO inicio;
      RAISE_APPLICATION_ERROR(-20002, 'Hub inexistente.');
    WHEN OTHERS THEN
      ROLLBACK TO inicio;
      RAISE;
  END pr_definir_hub_entrega;
END gestao_hubs;