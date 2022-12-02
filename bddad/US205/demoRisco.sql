DECLARE
id_cliente1 CLIENTE.id_cliente%TYPE;
id_cliente2 CLIENTE.id_cliente%TYPE;
n_encomendas_pendentes1 NUMBER;
n_encomendas_pendentes2 NUMBER;
valor_total_incidentes1 NUMBER;
valor_total_incidentes2 NUMBER;

BEGIN
DELETE FROM encomenda;
DELETE FROM produtoEncomenda;
DELETE FROM cliente;
DELETE FROM localidade;

INSERT INTO localidade VALUES ('4400-001', 'Porto');

  INSERT INTO cliente (id_cliente, nome, nif, email, morada, morada_entrega, plafond, cod_postal_entrega, cod_postal)
  VALUES (1, 'João', '123456789', 'joao@gmail.com', 'Rua do João', 'Rua do João de Entrega', 100, '4400-001', '4400-001')
  RETURNING id_cliente INTO id_cliente1;

  INSERT INTO cliente (id_cliente, nome, nif, email, morada, morada_entrega, plafond, cod_postal_entrega, cod_postal)
  VALUES (2, 'Maria', '123456789', 'maria@gmail.com', 'Rua da Maria', 'Rua da Maria de Entrega', 2, '4400-001', '4400-001')
  RETURNING id_cliente INTO id_cliente2;

  INSERT INTO Encomenda (id_encomenda,id_cliente,data_vencimento_pagamento,data_registo,data_entrega,data_pagamento,morada_entrega,cod_postal_entrega) VALUES (1,1,'10-Jan-2022','10-Jan-2020','20-Jan-2500',NULL,'Rua do João de Entrega','4400-001');

  INSERT INTO Encomenda (id_encomenda,id_cliente,data_vencimento_pagamento,data_registo,data_entrega,data_pagamento,morada_entrega,cod_postal_entrega) VALUES (2,2,'20-Jan-2022','10-Jan-2020','20-Jan-2500',NULL,'Rua da Maria de Entrega','4400-001');

  INSERT INTO ProdutoEncomenda (id_produto,id_encomenda,preco_unitario,quantidade,iva,designacao_produto) VALUES (1,1,10,10,23,'produto 1');

  INSERT INTO ProdutoEncomenda (id_produto,id_encomenda,preco_unitario,quantidade,iva,designacao_produto) VALUES (2,2,10,10,23,'produto 2');

  SELECT COUNT(*) INTO n_encomendas_pendentes1 FROM Encomenda WHERE (id_cliente1 = id_cliente1 AND data_pagamento IS NULL);

  SELECT COUNT(*) INTO n_encomendas_pendentes2 FROM Encomenda WHERE (id_cliente2 = id_cliente2 AND data_pagamento IS NULL);

  SELECT SUM((preco_unitario * (1 + iva / 100)) * quantidade) INTO valor_total_incidentes1 FROM produtoEncomenda INNER JOIN encomenda ON produtoEncomenda.id_encomenda = encomenda.id_encomenda WHERE id_cliente = cliente_id AND (data_pagamento > data_vencimento_pagamento OR (data_vencimento_pagamento < SYSDATE AND data_pagamento IS NULL));

  SELECT SUM((preco_unitario * (1 + iva / 100)) * quantidade) INTO valor_total_incidentes2 FROM produtoEncomenda INNER JOIN encomenda ON produtoEncomenda.id_encomenda = encomenda.id_encomenda WHERE id_cliente = cliente_id AND (data_pagamento > data_vencimento_pagamento OR (data_vencimento_pagamento < SYSDATE AND data_pagamento IS NULL));

  DBMS_OUTPUT.PUT_LINE('Encomendas Pendentes do Cliente 1: ' || n_encomendas_pendentes1);

  DBMS_OUTPUT.PUT_LINE(chr(13)||chr(10));

  DBMS_OUTPUT.PUT_LINE('Encomendas Pendentes do Cliente 2: ' || n_encomendas_pendentes2);

  DBMS_OUTPUT.PUT_LINE(chr(13)||chr(10));

  DBMS_OUTPUT.PUT_LINE('Valor Total Incidentes do Cliente 1: ' || valor_total_incidentes1);

  DBMS_OUTPUT.PUT_LINE(chr(13)||chr(10));

  DBMS_OUTPUT.PUT_LINE('Valor Total Incidentes do Cliente 2: ' || valor_total_incidentes2);

  DBMS_OUTPUT.PUT_LINE('Risco do cliente 1: ' || gestao_clientes.fn_risco_cliente(id_cliente1));

  DBMS_OUTPUT.PUT_LINE(chr(13)||chr(10));

  DBMS_OUTPUT.PUT_LINE('Risco do cliente 2: ' || gestao_clientes.fn_risco_cliente(id_cliente2));

  DBMS_OUTPUT.PUT_LINE(chr(13)||chr(10));

  END;




