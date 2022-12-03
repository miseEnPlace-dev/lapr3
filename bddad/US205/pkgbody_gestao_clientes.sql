CREATE OR REPLACE PACKAGE BODY gestao_clientes AS
  FUNCTION fn_registarCliente (
    nome CLIENTE.nome%TYPE,
    nif CLIENTE.nif%TYPE,
    email CLIENTE.email%TYPE,
    morada CLIENTE.morada%TYPE,
    morada_entrega CLIENTE.morada_entrega%TYPE,
    postal CLIENTE.cod_postal%TYPE,
    postal_entrega CLIENTE.cod_postal_entrega%TYPE,
    plafond CLIENTE.plafond%TYPE)
  RETURN CLIENTE.id_cliente%TYPE AS
    id_cliente CLIENTE.id_cliente%TYPE;
    cod_postal_var CLIENTE.cod_postal%TYPE;
    cod_postal_entrega_var CLIENTE.cod_postal_entrega%TYPE;
  BEGIN
    SAVEPOINT inicio;

    SELECT cod_postal INTO cod_postal_var FROM Localidade WHERE COD_POSTAL LIKE postal;

    SELECT cod_postal INTO cod_postal_entrega_var FROM Localidade WHERE COD_POSTAL LIKE postal_entrega;

    INSERT INTO cliente (nome, nif, email, morada, morada_entrega, plafond, cod_postal_entrega, cod_postal) VALUES (
      nome,
      nif,
      email,
      morada,
      morada_entrega,
      plafond,
      postal_entrega,
      postal) RETURNING id_cliente INTO id_cliente;

    DBMS_OUTPUT.PUT_LINE('Cliente ' || id_cliente || ' registado com sucesso.');

    COMMIT;
    RETURN id_cliente;
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

    SELECT id_cliente INTO null_id FROM cliente WHERE id_cliente = cliente_id;

    SELECT SUM((preco_unitario * (1 + iva / 100)) * quantidade), COUNT(*) INTO total_encomendas, num_encomendas
    FROM produtoEncomenda
    INNER JOIN encomenda ON produtoEncomenda.id_encomenda = encomenda.id_encomenda
    WHERE
      id_cliente = cliente_id
      AND data_registo >= trunc(sysdate, 'yyyy') - interval '1' year
      AND data_registo <  trunc(sysdate, 'yyyy');

    UPDATE Cliente SET valor_total_encomendas = total_encomendas, n_encomendas = num_encomendas WHERE id_cliente = cliente_id;

    DBMS_OUTPUT.PUT_LINE('Cliente ' || cliente_id || ' atualizado com sucesso.');

    EXCEPTION
    WHEN NO_DATA_FOUND THEN
      RAISE_APPLICATION_ERROR(-20001, 'Cliente inexistente.');
      ROLLBACK TO inicio;
  END pr_AtualizarEncomendasCliente;

  FUNCTION fn_risco_cliente (
    cliente_id CLIENTE.id_cliente%TYPE)
    RETURN NUMBER IS
    risco NUMBER;
    n_encomendas_pendentes NUMBER;
    valor_total_incidentes NUMBER;

  BEGIN

  risco := 0;

  SELECT SUM((preco_unitario * (1 + iva / 100)) * quantidade) INTO valor_total_incidentes
  FROM produtoEncomenda
  INNER JOIN encomenda ON produtoEncomenda.id_encomenda = encomenda.id_encomenda
  WHERE id_cliente = cliente_id AND (data_pagamento > data_vencimento_pagamento OR (data_vencimento_pagamento < SYSDATE AND data_pagamento IS NULL))
  GROUP BY id_cliente;


  SELECT COUNT(*) INTO n_encomendas_pendentes
  FROM Encomenda
  WHERE (id_cliente = cliente_id AND data_pagamento IS NULL);

  IF n_encomendas_pendentes > 0 THEN
    risco := valor_total_incidentes / n_encomendas_pendentes;
  ELSE
    RAISE NO_DATA_FOUND;
  END IF;

  EXCEPTION
    WHEN NO_DATA_FOUND THEN
      RETURN risco := valor_total_incidentes;

  RETURN risco;
  END fn_risco_cliente;

END gestao_clientes;
