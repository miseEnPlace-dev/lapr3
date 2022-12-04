DECLARE
  cliente_id1 CLIENTE.id_cliente%TYPE;
  n_encomendas NUMBER;

BEGIN
DELETE FROM Cliente;
DELETE FROM Encomenda;
DELETE FROM ProdutoEncomenda;

gestao_clientes.pr_AtualizarEncomendasCliente(2);
END;
