DECLARE
  lista_produtos gestao_encomendas.produtos;
  id_escalao_iva ESCALAOIVA.id_escalao_iva%TYPE;
  id_banana PRODUTO.id_produto%TYPE;
  id_maca PRODUTO.id_produto%TYPE;
  id_cliente CLIENTE.id_cliente%TYPE;
  id_encomenda ENCOMENDA.id_encomenda%TYPE;
BEGIN
  DELETE FROM encomenda;
  DELETE FROM cliente;
  DELETE FROM produto;
  DELETE FROM escalaoiva;
  DELETE FROM localidade;

  INSERT INTO localidade VALUES ('4400-001', 'Porto');

  INSERT INTO cliente (nome, nif, email, morada, morada_entrega, plafond, cod_postal_entrega, cod_postal) 
  VALUES ('João', '123456789', 'joao@gmail.com', 'Rua do João', 'Rua do Hub', 100, '4400-001', '4400-001')
  RETURNING id_cliente INTO id_cliente;

  INSERT INTO escalaoiva (valor)
  VALUES (6)
  RETURNING id_escalao_iva INTO id_escalao_iva;

  INSERT INTO produto (designacao, preco, id_escalao_iva)
  VALUES ('Banana da Madeira', 0.50, id_escalao_iva)
  RETURNING id_produto INTO id_banana;
  
  INSERT INTO produto (designacao, preco, id_escalao_iva)
  VALUES ('Maçã de Alcobaça', 1.00, id_escalao_iva)
  RETURNING id_produto INTO id_maca;

  lista_produtos(id_banana) := 2;
  lista_produtos(id_maca) := 1;

  DBMS_OUTPUT.PUT_LINE(chr(13)||chr(10));

  gestao_encomendas.listar_encomendas();

  DBMS_OUTPUT.PUT_LINE(chr(13)||chr(10));

  id_encomenda := gestao_encomendas.registar_encomenda(id_cliente, lista_produtos);

  DBMS_OUTPUT.PUT_LINE(chr(13)||chr(10));

  id_encomenda := gestao_encomendas.registar_encomenda(id_cliente, lista_produtos, 'Rua do João', '4400-001');

  DBMS_OUTPUT.PUT_LINE(chr(13)||chr(10));

  gestao_encomendas.listar_encomendas();

  DBMS_OUTPUT.PUT_LINE(chr(13)||chr(10));

  gestao_encomendas.registar_pagamento(id_encomenda, SYSDATE);

  DBMS_OUTPUT.PUT_LINE(chr(13)||chr(10));

  gestao_encomendas.listar_encomendas();

  DBMS_OUTPUT.PUT_LINE(chr(13)||chr(10));

  gestao_encomendas.listar_encomendas_pagas();

  DBMS_OUTPUT.PUT_LINE(chr(13)||chr(10));

  gestao_encomendas.listar_encomendas_entregues();

  DBMS_OUTPUT.PUT_LINE(chr(13)||chr(10));

  gestao_encomendas.registar_entrega(id_encomenda, SYSDATE);

  DBMS_OUTPUT.PUT_LINE(chr(13)||chr(10));

  gestao_encomendas.listar_encomendas();
END;