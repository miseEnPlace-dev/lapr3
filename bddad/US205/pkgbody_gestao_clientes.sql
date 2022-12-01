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
    IF cod_postal_entrega_var IS NULL THEN
      RAISE cod_postal_inexistente;
    END IF;

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
    IF null_id IS NULL THEN
      RAISE cliente_inexistente;
    END IF;

    SELECT SUM((preco_unitario * (1 + iva / 100)) * quantidade), COUNT(*) INTO total_encomendas, num_encomendas
    FROM produtoEncomenda
    INNER JOIN encomenda ON produtoEncomenda.id_encomenda = encomenda.id_encomenda
    WHERE id_cliente = cliente_id;

    UPDATE Cliente SET valor_total_encomendas = total_encomendas, n_encomendas = num_encomendas WHERE id_cliente = cliente_id;

    DBMS_OUTPUT.PUT_LINE('Cliente ' || cliente_id || ' atualizado com sucesso.');

    EXCEPTION
    WHEN NO_DATA_FOUND THEN
      RAISE_APPLICATION_ERROR(-20001, 'Cliente inexistente.');
      ROLLBACK TO inicio;
  END atualizar_encomendas_cliente;
END gestao_clientes;

DECLARE
    id_cliente NUMBER;
BEGIN
    id_cliente := gestao_clientes.registar_cliente('name',123456789,'email@email.com','morada','morada','4500-123','4400-456',100);
END;

BEGIN
  gestao_clientes.atualizar_encomendas_cliente(1);
END;
