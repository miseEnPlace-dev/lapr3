CREATE OR REPLACE PACKAGE BODY gestao_clientes AS
  FUNCTION registar_cliente (
    id_cliente CLIENTE.id_cliente%TYPE,
    nome CLIENTE.nome,
    nif CLIENTE.nif%TYPE,
    email CLIENTE.email%TYPE,
    morada CLIENTE.morada%TYPE,
    morada_entrega CLIENTE.morada_entrega%TYPE,
    plafond CLIENTE.plafond%TYPE,
    cod_postal CLIENTE.cod_postal%TYPE,
    cod_postal_entrega CLIENTE.cod_postal_entrega%TYPE)
  RETURN Cliente.id_encomenda%TYPE;
  BEGIN
    SAVEPOINT inicio;

    SELECT cod_postal INTO cod_postal FROM Localidade WHERE cod_postal = cod_postal;
    IF cod_postal IS NULL THEN
      RAISE cod_postal_inexistente;
    END IF;

    SELECT cod_postal INTO cod_postal_entrega FROM Localidade WHERE cod_postal = cod_postal_entrega;
    IF cod_postal_entrega IS NULL THEN
      RAISE cod_postal_inexistente;
    END IF;

    INSERT INTO cliente (id_cliente, nome, nif, email, morada, morada_entrega, plafond, cod_postal, cod_postal_entrega) VALUES (
      id_cliente,
      nome,
      nif,
      email,
      morada,
      morada_entrega,
      plafond,
      cod_postal,
      cod_postal_entrega);

    DBMS_OUTPUT.PUT_LINE('Cliente ' || id_cliente || ' registado com sucesso.');

    COMMIT;
    RETURN id_cliente;
  EXCEPTION
    WHEN cod_postal_inexistente THEN
      RAISE_APPLICATION_ERROR(-20001, 'Código postal inexistente.');
      ROLLBACK TO inicio;
  END registar_cliente;
END gestao_clientes;
