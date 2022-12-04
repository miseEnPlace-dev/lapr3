DECLARE
  cliente_id1 CLIENTE.id_cliente%TYPE;
  n_encomendas NUMBER;

BEGIN
DELETE FROM Cliente;
DELETE FROM Encomenda;
DELETE FROM ProdutoEncomenda;

INSERT INTO Cliente VALUES (1, 'João', 123456789, 'joao@gmail.com', 'Rua do João', 'Rua do João', 100, '1234-567', '1234-567',0,0)
returning id_cliente into cliente_id1;

INSERT INTO Encomenda VALUES (1, cliente_id1, '30-Jan-2022', '02-Jan-2022','12-Jan-2022','03-Feb-2022','morada','1234-567');
INSERT INTO Encomenda VALUES (2, cliente_id1, '30-Jan-2022', '02-Jan-2022','12-Jan-2022','03-Feb-2022','morada','1234-567');


INSERT INTO ProdutoEncomenda (id_produto,id_encomenda,preco_unitario,quantidade,iva,designacao_produto) VALUES (1,1,10,10,23,'produto 1');

gestao_clientes.pr_AtualizarEncomendasCliente(cliente_id1);

SELECT COUNT (*) INTO n_encomendas FROM Encomenda WHERE id_cliente = cliente_id1;

DBMS_OUTPUT.PUT_LINE('Numero de encomendas: ' || n_encomendas);

END;
