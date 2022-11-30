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
    IF cod_postal_var IS NULL THEN
      RAISE cod_postal_inexistente;
    END IF;

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
    WHEN cod_postal_inexistente THEN
      RAISE_APPLICATION_ERROR(-20001, 'Código postal inexistente.');
      ROLLBACK TO inicio;
    WHEN NO_DATA_FOUND THEN
      RAISE_APPLICATION_ERROR(-20002, 'Código postal inexistente.');
      ROLLBACK TO inicio;
  END registar_cliente;
END gestao_clientes;

DECLARE
    id_cliente NUMBER;
BEGIN
    id_cliente := gestao_clientes.registar_cliente('name',123456789,'email@email.com','morada','morada','4500-123','4400-456',100);
END;
