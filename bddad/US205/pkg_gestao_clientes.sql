CREATE OR REPLACE PACKAGE gestao_clientes AS
  FUNCTION fn_RegistarCliente(
      nome CLIENTE.nome%TYPE,
      nif CLIENTE.nif%TYPE,
      email CLIENTE.email%TYPE,
      morada CLIENTE.morada%TYPE,
      morada_entrega CLIENTE.morada_entrega%TYPE,
      postal CLIENTE.cod_postal%TYPE,
      postal_entrega CLIENTE.cod_postal_entrega%TYPE,
      plafond CLIENTE.plafond%TYPE)
    RETURN CLIENTE.id_cliente%TYPE;

  PROCEDURE pr_AtualizarEncomendasCliente(
    cliente_id CLIENTE.id_cliente%TYPE);

  FUNCTION fn_risco_cliente(
    cliente_id CLIENTE.id_cliente%TYPE);
    RETURN NUMBER;

END gestao_clientes;
