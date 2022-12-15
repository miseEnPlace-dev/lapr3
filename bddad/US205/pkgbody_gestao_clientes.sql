CREATE OR REPLACE PACKAGE BODY gestao_clientes AS
  FUNCTION fn_RegistarCliente (
    nome CLIENTE.nome%TYPE,
    nif CLIENTE.nif%TYPE,
    email CLIENTE.email%TYPE,
    morada CLIENTE.morada%TYPE,
    morada_entrega CLIENTE.morada_entrega%TYPE,
    postal CLIENTE.cod_postal%TYPE,
    postal_entrega CLIENTE.cod_postal_entrega%TYPE,
    plafond CLIENTE.plafond%TYPE)
  RETURN CLIENTE.id_cliente%TYPE AS
    cliente_id CLIENTE.id_cliente%TYPE;
    cod_postal_var CLIENTE.cod_postal%TYPE;
    cod_postal_entrega_var CLIENTE.cod_postal_entrega%TYPE;
  BEGIN
    SAVEPOINT inicio;

    SELECT cod_postal INTO cod_postal_var FROM Localidade WHERE COD_POSTAL LIKE postal;

    SELECT cod_postal INTO cod_postal_entrega_var FROM Localidade WHERE COD_POSTAL LIKE postal_entrega;

    -- get last id of cliente
    SELECT MAX(id_cliente) INTO cliente_id FROM Cliente;

    -- if null start in 1
    IF cliente_id IS NULL THEN
      cliente_id := 1;
    ELSE
      cliente_id := cliente_id + 1;
    END IF;

    INSERT INTO CLIENTE (id_cliente,nome, nif, email, morada, morada_entrega, plafond, cod_postal_entrega, cod_postal) VALUES (
      cliente_id,
      nome,
      nif,
      email,
      morada,
      morada_entrega,
      plafond,
      postal_entrega,
      postal);

    DBMS_OUTPUT.PUT_LINE('Cliente ' || cliente_id || ' registado com sucesso.');

    registar_logs.pr_RegistarInsert(USER, sysdate,'CLIENTE');


    COMMIT;
    RETURN cliente_id;
  EXCEPTION
    WHEN NO_DATA_FOUND THEN
      RAISE_APPLICATION_ERROR(-20002, 'Código postal inexistente.');
      ROLLBACK TO inicio;
  END fn_registarCliente;

  PROCEDURE pr_AtualizarEncomendasCliente(cliente_id CLIENTE.id_cliente%TYPE) IS
    total_encomendas NUMBER;
    num_encomendas NUMBER;
    null_id CLIENTE.id_cliente%TYPE;
  BEGIN
    SAVEPOINT inicio;

    SELECT id_cliente INTO null_id FROM CLIENTE WHERE id_cliente = cliente_id;

    SELECT SUM((preco_unitario * (1 + iva / 100)) * quantidade), COUNT(*) INTO total_encomendas, num_encomendas
    FROM produtoEncomenda
    INNER JOIN encomenda ON produtoEncomenda.id_encomenda = encomenda.id_encomenda
    WHERE
      id_cliente = cliente_id
      AND data_registo >= sysdate - 365;

    IF (total_encomendas IS NULL or total_encomendas = 0) AND (num_encomendas = 0 OR num_encomendas IS NULL) THEN
      DBMS_OUTPUT.PUT_LINE('Cliente ' || cliente_id || ' nao foi atualizado.');
      ROLLBACK TO fim;
    END IF;

    UPDATE CLIENTE SET valor_total_encomendas = total_encomendas, n_encomendas = num_encomendas WHERE id_cliente = cliente_id;

    DBMS_OUTPUT.PUT_LINE('Cliente ' || cliente_id || ' atualizado com sucesso.');

    registar_logs.pr_RegistarUpdate(USER, sysdate,'CLIENTE');

    EXCEPTION
      WHEN NO_DATA_FOUND THEN
        RAISE_APPLICATION_ERROR(-20002, 'Cliente inexistente.');
        ROLLBACK TO inicio;
    ROLLBACK TO inicio;

    SAVEPOINT fim;
  END pr_AtualizarEncomendasCliente;

  FUNCTION fn_RiscoCliente (
    cliente_id CLIENTE.id_cliente%TYPE)
    RETURN NUMBER IS
    risco NUMBER;
    n_encomendas_pendentes NUMBER;
    valor_total_incidentes NUMBER;
    data_ultimo_incidente varchar2(50);

  BEGIN

  risco := 0;

  SELECT NumEncomendasPendentes,valor_total_incidentes,data_ultimo_incidente
  INTO n_encomendas_pendentes, valor_total_incidentes, data_ultimo_incidente
  FROM Cliente_View
  WHERE id_cliente = cliente_id;

  IF n_encomendas_pendentes > 0 THEN
    risco := valor_total_incidentes / n_encomendas_pendentes;
  ELSE
    risco := valor_total_incidentes;
  END IF;

  IF ((n_encomendas_pendentes = 0 OR n_encomendas_pendentes IS NULL) AND (valor_total_incidentes = 0 OR valor_total_incidentes IS NULL) AND data_ultimo_incidente = 'Sem incidentes à data') THEN
     DBMS_OUTPUT.PUT_LINE('Sem dados para o cliente ' || cliente_id);
     DBMS_OUTPUT.PUT_LINE(chr(13)||chr(10));
     risco := 0;
  END IF;

  RETURN risco;
  END fn_RiscoCliente;

END gestao_clientes;
