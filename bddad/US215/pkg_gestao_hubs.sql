CREATE OR REPLACE PACKAGE gestao_hubs AS
  /* Permite atualizar a tabela Hub, a partir da tabela InputHub. */
  PROCEDURE pr_atualizar_hubs;

  /* Permite definir/atualizar o Hub de entrega de um cliente.
  *
  * Recebe o id do cliente e o id do Hub de entrega. */
  PROCEDURE pr_definir_hub_entrega (
    p_id_cliente CLIENTE.id_cliente%TYPE,
    p_id_hub_entrega HUB.id_hub%TYPE
  );
  
  /* Exceção lançada quando o hub não existe. */
  e_hub_inexistente EXCEPTION;

  /* Exceção lançada quando o cliente não existe. */
  e_cliente_inexistente EXCEPTION;
END gestao_hubs;