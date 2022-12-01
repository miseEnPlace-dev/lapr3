CREATE OR REPLACE PACKAGE BODY gestao_clientes AS
  FUNCTION registar_cliente (
    nome IN CLIENTE.nome%TYPE,
    nif IN CLIENTE.nif%TYPE,
    email IN CLIENTE.email%TYPE,
    morada IN CLIENTE.morada%TYPE,
    morada_entrega IN CLIENTE.morada_entrega%TYPE,
    postal IN CLIENTE.cod_postal%TYPE,
    postal_entrega IN CLIENTE.cod_postal_entrega%TYPE,
    plafond IN CLIENTE.plafond%TYPE)
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
  END registar_cliente;

  PROCEDURE atualizar_encomendas_cliente(cliente_id IN CLIENTE.id_cliente%TYPE) IS
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
  END atualizar_encomendas_cliente;
END gestao_clientes;
