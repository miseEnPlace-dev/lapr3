CREATE OR REPLACE PACKAGE gestao_clientes AS
  TYPE clientes IS TABLE OF NUMBER INDEX BY BINARY_INTEGER;

FUNCTION registar_cliente(
    nome IN CLIENTE.nome%TYPE,
    nif IN CLIENTE.nif%TYPE,
    email IN CLIENTE.email%TYPE,
    morada IN CLIENTE.morada%TYPE,
    morada_entrega IN CLIENTE.morada_entrega%TYPE,
    postal IN CLIENTE.cod_postal%TYPE,
    postal_entrega IN CLIENTE.cod_postal_entrega%TYPE,
    plafond IN CLIENTE.plafond%TYPE)
  RETURN CLIENTE.id_cliente%TYPE;

  cod_postal_inexistente EXCEPTION;
END gestao_clientes;
