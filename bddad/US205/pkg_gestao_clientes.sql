CREATE OR REPLACE PACKAGE gestao_clientes AS
  TYPE clientes IS TABLE OF NUMBER INDEX BY BINARY_INTEGER;

FUNCTION registar_cliente(id_cliente CLIENTE.id_cliente%TYPE,
    nome CLIENTE.id_cliente,
    morada_entrega ENCOMENDA.morada_entrega%TYPE,
    cod_postal_entrega ENCOMENDA.cod_postal_entrega%TYPE)
  RETURN ENCOMENDA.id_encomenda%TYPE;

END gestao_clientes;
