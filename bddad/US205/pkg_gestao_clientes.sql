CREATE OR REPLACE PACKAGE gestao_clientes AS
  TYPE clientes IS TABLE OF NUMBER INDEX BY BINARY_INTEGER;

FUNCTION registar_cliente(
    nome CLIENTE.nome%TYPE,
    nif CLIENTE.nif%TYPE,
    email CLIENTE.email%TYPE,
    morada CLIENTE.morada%TYPE,
    morada_entrega CLIENTE.morada_entrega%TYPE,
    plafond CLIENTE.plafond%TYPE)
  RETURN Cliente.id_cliente%TYPE;

  cod_postal_inexistente EXCEPTION;
END gestao_clientes;
