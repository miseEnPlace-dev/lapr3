CREATE OR REPLACE PACKAGE BODY gestao_clientes AS
  FUNCTION registar_cliente (
    nome CLIENTE.nome%TYPE,
    nif CLIENTE.nif%TYPE,
    email CLIENTE.email%TYPE,
    morada CLIENTE.morada%TYPE,
    morada_entrega CLIENTE.morada_entrega%TYPE,
    plafond CLIENTE.plafond%TYPE)
  RETURN Cliente.id_cliente%TYPE IS
    id_cliente Cliente.id_cliente%TYPE;
    cod_postal Cliente.cod_postal%TYPE;
    cod_postal_entrega Cliente.cod_postal_entrega%TYPE;
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
      cod_postal_entrega) RETURNING id_cliente INTO id_cliente;

    DBMS_OUTPUT.PUT_LINE('Cliente ' || id_cliente || ' registado com sucesso.');

    COMMIT;
    RETURN id_cliente;
  EXCEPTION
    WHEN cod_postal_inexistente THEN
      RAISE_APPLICATION_ERROR(-20001, 'Código postal inexistente.');
      ROLLBACK TO inicio;
  END registar_cliente;
END gestao_clientes;

CALL registar_cliente('estiq la', 123456789, 'email@email.com', 'morada', 'morada entrega', 1000, 1234, 1234);
