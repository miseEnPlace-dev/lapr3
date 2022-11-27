CREATE OR REPLACE PACKAGE gestao_clientes AS
  TYPE clientes IS TABLE OF NUMBER INDEX BY BINARY_INTEGER;

FUNCTION registar_cliente(
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

END gestao_clientes;
