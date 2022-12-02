CREATE OR REPLACE PACKAGE BODY gestao_encomendas AS
  FUNCTION registar_encomenda(id_cliente CLIENTE.id_cliente%TYPE, 
    lista_produtos produtos,
    data_registo ENCOMENDA.data_registo%TYPE) 
  RETURN ENCOMENDA.id_encomenda%TYPE IS
    id_enc ENCOMENDA.id_encomenda%TYPE;
    id_prod PRODUTO.id_produto%TYPE;
    id_cli CLIENTE.id_cliente%TYPE;
    preco_produto PRODUTO.preco%TYPE;
    iva_produto ESCALAOIVA.valor%TYPE;
    designacao_produto PRODUTO.designacao%TYPE;
    morada_entrega CLIENTE.morada_entrega%TYPE;
    cod_postal_entrega CLIENTE.cod_postal_entrega%TYPE;
    plafond_cliente CLIENTE.plafond%TYPE;
    valor_encomenda NUMBER;
    valor_por_pagar NUMBER;
  BEGIN
    SAVEPOINT inicio;

    id_cli := id_cliente;

    SELECT morada_entrega, cod_postal_entrega, plafond 
    INTO morada_entrega, cod_postal_entrega, plafond_cliente 
    FROM cliente 
    WHERE id_cliente = id_cli;
  
    IF morada_entrega IS NULL THEN
      RAISE cliente_inexistente;
    END IF;

    SELECT MAX(id_encomenda) INTO id_enc FROM encomenda;

    IF id_enc IS NULL THEN
      id_enc := 1;
    ELSE
      id_enc := id_enc + 1;
    END IF;

    INSERT INTO encomenda (id_encomenda, id_cliente, data_vencimento_pagamento, data_registo, morada_entrega, cod_postal_entrega)
    VALUES (id_enc, id_cliente, data_registo + 30, data_registo, morada_entrega, cod_postal_entrega);

    valor_encomenda := 0;

    id_prod := lista_produtos.FIRST;

    WHILE id_prod IS NOT NULL LOOP
      SELECT preco, designacao 
      INTO preco_produto, designacao_produto 
      FROM produto 
      WHERE id_produto = id_prod;
  
      IF preco_produto IS NULL THEN
        RAISE produto_inexistente;
      END IF;
  
      SELECT valor
      INTO iva_produto
      FROM escalaoiva e, produto p
      WHERE p.id_produto = id_prod AND e.id_escalao_iva = p.id_escalao_iva;

      INSERT INTO produtoencomenda (id_encomenda, id_produto, quantidade, preco_unitario, iva, designacao_produto)
      VALUES (id_enc, id_prod, lista_produtos(id_prod), preco_produto, iva_produto, designacao_produto);

      id_prod := lista_produtos.NEXT(id_prod);
    END LOOP;

    SELECT SUM(preco_unitario * quantidade * (1 + iva/100))
    INTO valor_por_pagar
    FROM produtoencomenda 
    WHERE id_encomenda IN (SELECT id_encomenda FROM encomenda WHERE id_cliente = id_cli AND data_pagamento IS NULL);

    IF valor_por_pagar > plafond_cliente THEN
      RAISE sem_plafond;
    END IF;

    DBMS_OUTPUT.PUT_LINE('Encomenda ' || id_enc || ' registada com sucesso.');

    COMMIT;
    RETURN id_enc;
  EXCEPTION
    WHEN cliente_inexistente THEN
      RAISE_APPLICATION_ERROR(-20001, 'Cliente inexistente.');
      ROLLBACK TO inicio;
    WHEN produto_inexistente THEN
      RAISE_APPLICATION_ERROR(-20002, 'Produto inexistente.');
      ROLLBACK TO inicio;
    WHEN sem_plafond THEN
      RAISE_APPLICATION_ERROR(-20003, 'Sem plafond.');
      ROLLBACK TO inicio;
    WHEN OTHERS THEN
      RAISE_APPLICATION_ERROR(-20005, 'Erro ao registar encomenda.');
      ROLLBACK TO inicio;
  END registar_encomenda;

  FUNCTION registar_encomenda(id_cliente CLIENTE.id_cliente%TYPE, 
    lista_produtos produtos, 
    morada_entrega ENCOMENDA.morada_entrega%TYPE, 
    cod_postal_entrega ENCOMENDA.cod_postal_entrega%TYPE,
    data_registo ENCOMENDA.data_registo%TYPE) 
  RETURN ENCOMENDA.id_encomenda%TYPE IS
    id_enc ENCOMENDA.id_encomenda%TYPE;
    id_prod PRODUTO.id_produto%TYPE;
    id_cli CLIENTE.id_cliente%TYPE;
    preco_produto PRODUTO.preco%TYPE;
    iva_produto ESCALAOIVA.valor%TYPE;
    designacao_produto PRODUTO.designacao%TYPE;
    plafond_cliente CLIENTE.plafond%TYPE;
    valor_encomenda NUMBER;
    valor_por_pagar NUMBER;
  BEGIN
    SAVEPOINT inicio;

    id_cli := id_cliente;

    SELECT plafond 
    INTO plafond_cliente 
    FROM cliente 
    WHERE id_cliente = id_cli;
  
    IF plafond_cliente IS NULL THEN
      RAISE cliente_inexistente;
    END IF;

    SELECT MAX(id_encomenda) INTO id_enc FROM encomenda;

    IF id_enc IS NULL THEN
      id_enc := 1;
    ELSE
      id_enc := id_enc + 1;
    END IF;

    INSERT INTO encomenda (id_encomenda, id_cliente, data_vencimento_pagamento, data_registo, morada_entrega, cod_postal_entrega)
    VALUES (id_enc, id_cliente, data_registo + 30, data_registo, morada_entrega, cod_postal_entrega);

    valor_encomenda := 0;

    id_prod := lista_produtos.FIRST;

    WHILE id_prod IS NOT NULL LOOP
      SELECT preco, designacao 
      INTO preco_produto, designacao_produto 
      FROM produto
      WHERE id_produto = id_prod;
  
      IF preco_produto IS NULL THEN
        RAISE produto_inexistente;
      END IF;
  
      SELECT valor
      INTO iva_produto
      FROM escalaoiva e, produto p
      WHERE p.id_produto = id_prod AND e.id_escalao_iva = p.id_escalao_iva;

      INSERT INTO produtoencomenda (id_encomenda, id_produto, quantidade, preco_unitario, iva, designacao_produto)
      VALUES (id_enc, id_prod, lista_produtos(id_prod), preco_produto, iva_produto, designacao_produto);

      id_prod := lista_produtos.NEXT(id_prod);
    END LOOP;

    SELECT SUM(preco_unitario * quantidade * (1 + iva/100))
    INTO valor_por_pagar
    FROM produtoencomenda 
    WHERE id_encomenda IN (SELECT id_encomenda FROM encomenda WHERE id_cliente = id_cli AND data_pagamento IS NULL);

    IF valor_por_pagar > plafond_cliente THEN
      RAISE sem_plafond;
    END IF;

    DBMS_OUTPUT.PUT_LINE('Encomenda ' || id_enc || ' registada com sucesso.');

    COMMIT;
    RETURN id_enc;
  EXCEPTION
    WHEN cliente_inexistente THEN
      RAISE_APPLICATION_ERROR(-20001, 'Cliente inexistente.');
      ROLLBACK TO inicio;
    WHEN produto_inexistente THEN
      RAISE_APPLICATION_ERROR(-20002, 'Produto inexistente.');
      ROLLBACK TO inicio;
    WHEN sem_plafond THEN
      RAISE_APPLICATION_ERROR(-20003, 'Sem plafond.');
      ROLLBACK TO inicio;
    WHEN OTHERS THEN
      RAISE_APPLICATION_ERROR(-20005, 'Erro ao registar entrega.');
      ROLLBACK TO inicio;
  END registar_encomenda;

  PROCEDURE registar_entrega(id_encomenda ENCOMENDA.id_encomenda%TYPE, 
    data_entrega ENCOMENDA.data_entrega%TYPE) IS
    cont NUMBER;
    id_enc ENCOMENDA.id_encomenda%TYPE;
    data_ent ENCOMENDA.data_entrega%TYPE;
  BEGIN
    SAVEPOINT inicio;

    id_enc := id_encomenda;
    data_ent := data_entrega;

    SELECT COUNT(*) 
    INTO cont
    FROM encomenda
    WHERE id_encomenda = id_enc AND data_entrega IS NULL;

    IF cont = 0 THEN
      RAISE encomenda_inexistente;
    END IF;

    UPDATE encomenda
    SET (data_entrega) = data_ent
    WHERE id_encomenda = id_enc;

    DBMS_OUTPUT.PUT_LINE('Entrega da encomenda ' || id_enc || ' registada com sucesso.');

    COMMIT;
  EXCEPTION
    WHEN encomenda_inexistente THEN
      RAISE_APPLICATION_ERROR(-20004, 'Encomenda inexistente.');
      ROLLBACK TO inicio;
    WHEN OTHERS THEN
      RAISE_APPLICATION_ERROR(-20005, 'Erro ao registar entrega.');
      ROLLBACK TO inicio;
  END registar_entrega;

  PROCEDURE registar_pagamento(id_encomenda ENCOMENDA.id_encomenda%TYPE, 
    data_pagamento ENCOMENDA.data_pagamento%TYPE) IS
    cont NUMBER;
    id_enc ENCOMENDA.id_encomenda%TYPE;
    data_pag ENCOMENDA.data_pagamento%TYPE;
  BEGIN
    SAVEPOINT inicio;

    id_enc := id_encomenda;
    data_pag := data_pagamento;

    SELECT COUNT(*) 
    INTO cont
    FROM encomenda
    WHERE id_encomenda = id_enc AND data_pagamento IS NULL;

    IF cont = 0 THEN
      RAISE encomenda_inexistente;
    END IF;

    UPDATE encomenda
    SET (data_pagamento) = data_pag
    WHERE id_encomenda = id_enc;

    DBMS_OUTPUT.PUT_LINE('Pagamento da encomenda ' || id_enc || ' registado com sucesso.');

    COMMIT;
  EXCEPTION
    WHEN encomenda_inexistente THEN
      RAISE_APPLICATION_ERROR(-20004, 'Encomenda inexistente.');
      ROLLBACK TO inicio;
    WHEN OTHERS THEN
      RAISE_APPLICATION_ERROR(-20005, 'Erro ao registar pagamento.');
      ROLLBACK TO inicio;
  END registar_pagamento;

  PROCEDURE listar_encomendas IS
    CURSOR encomendas IS
      SELECT id_encomenda, id_cliente, data_entrega, data_pagamento, data_registo
      FROM encomenda
      ORDER BY id_encomenda;
    id_enc ENCOMENDA.id_encomenda%TYPE;
    id_cli ENCOMENDA.id_cliente%TYPE;
    nome_cliente CLIENTE.nome%TYPE;
    valor FLOAT;
    data_entrega ENCOMENDA.data_entrega%TYPE;
    data_pagamento ENCOMENDA.data_pagamento%TYPE;
    data_registo ENCOMENDA.data_registo%TYPE;
  BEGIN
    OPEN encomendas;
    FETCH encomendas INTO id_enc, id_cli, data_entrega, data_pagamento, data_registo;
  
    IF encomendas%NOTFOUND THEN
      DBMS_OUTPUT.PUT_LINE('Não existem encomendas.');
    ELSE
      DBMS_OUTPUT.PUT_LINE('Encomendas:');
      DBMS_OUTPUT.PUT_LINE('');

      LOOP
        EXIT WHEN encomendas%NOTFOUND;

        DBMS_OUTPUT.PUT_LINE('----');

        SELECT SUM(preco_unitario * quantidade * (1 + iva/100))
        INTO valor
        FROM produtoencomenda 
        WHERE id_encomenda = id_enc;

        SELECT nome
        INTO nome_cliente
        FROM cliente
        WHERE id_cliente = id_cli;

        DBMS_OUTPUT.PUT_LINE('Encomenda ' || id_enc || ' do cliente ' || nome_cliente || ' no valor de ' || TRUNC(valor, 2) || ' euros registada a ' || TO_CHAR(data_registo, 'DD/MM/YYYY') || '.');

        IF data_entrega IS NOT NULL THEN
          DBMS_OUTPUT.PUT_LINE('Entregue a ' || TO_CHAR(data_entrega, 'DD/MM/YYYY') || '.');
        END IF;

        IF data_pagamento IS NOT NULL THEN
          DBMS_OUTPUT.PUT_LINE('Pagamento registado a ' || TO_CHAR(data_pagamento, 'DD/MM/YYYY') || '.');
        END IF;

        IF data_pagamento IS NOT NULL THEN
          DBMS_OUTPUT.PUT_LINE('Estado: PAGA.');
        ELSIF data_entrega IS NOT NULL THEN
          DBMS_OUTPUT.PUT_LINE('Estado: ENTREGUE.');
        ELSE
          DBMS_OUTPUT.PUT_LINE('Estado: REGISTADA.');
        END IF;

        FETCH encomendas INTO id_enc, id_cli, data_entrega, data_pagamento, data_registo;
      END LOOP;
    END IF;

    CLOSE encomendas;
  END listar_encomendas;

  PROCEDURE listar_encomendas_registadas IS
    CURSOR encomendas IS
      SELECT id_encomenda, id_cliente, data_registo
      FROM encomenda
      WHERE data_entrega IS NULL AND data_pagamento IS NULL
      ORDER BY id_encomenda;
    id_enc ENCOMENDA.id_encomenda%TYPE;
    id_cli ENCOMENDA.id_cliente%TYPE;
    nome_cliente CLIENTE.nome%TYPE;
    valor FLOAT;
    data_registo ENCOMENDA.data_registo%TYPE;
  BEGIN
    OPEN encomendas;
    FETCH encomendas INTO id_enc, id_cli, data_registo;

    IF encomendas%NOTFOUND THEN
      DBMS_OUTPUT.PUT_LINE('Não existem encomendas registadas.');
    ELSE
      DBMS_OUTPUT.PUT_LINE('Encomendas REGISTADAS:');
      DBMS_OUTPUT.PUT_LINE('');

      LOOP
        EXIT WHEN encomendas%NOTFOUND;

        DBMS_OUTPUT.PUT_LINE('----');

        SELECT SUM(preco_unitario * quantidade * (1 + iva/100))
        INTO valor
        FROM produtoencomenda 
        WHERE id_encomenda = id_enc;

        SELECT nome
        INTO nome_cliente
        FROM cliente
        WHERE id_cliente = id_cli;

        DBMS_OUTPUT.PUT_LINE('Encomenda ' || id_enc || ' do cliente ' || nome_cliente || ' no valor de ' || TRUNC(valor, 2) || ' euros registada a ' || TO_CHAR(data_registo, 'DD/MM/YYYY') || '.');

        DBMS_OUTPUT.PUT_LINE('Estado: REGISTADA.');

        FETCH encomendas INTO id_enc, id_cli, data_registo;
      END LOOP;
    END IF;

    CLOSE encomendas;
  END listar_encomendas_registadas;

  PROCEDURE listar_encomendas_entregues IS
    CURSOR encomendas IS
      SELECT id_encomenda, id_cliente, data_entrega, data_registo
      FROM encomenda
      WHERE data_entrega IS NOT NULL AND data_pagamento IS NULL
      ORDER BY id_encomenda;
    id_enc ENCOMENDA.id_encomenda%TYPE;
    id_cli ENCOMENDA.id_cliente%TYPE;
    nome_cliente CLIENTE.nome%TYPE;
    data_entrega ENCOMENDA.data_entrega%TYPE;
    data_registo ENCOMENDA.data_registo%TYPE;
    valor FLOAT;
  BEGIN
    OPEN encomendas;
    FETCH encomendas INTO id_enc, id_cli, data_entrega, data_registo;

    IF encomendas%NOTFOUND THEN
      DBMS_OUTPUT.PUT_LINE('Não existem encomendas entregues.');
    ELSE
      DBMS_OUTPUT.PUT_LINE('Encomendas ENTREGUES:');
      DBMS_OUTPUT.PUT_LINE('');

      LOOP
        EXIT WHEN encomendas%NOTFOUND;

        DBMS_OUTPUT.PUT_LINE('----');

        SELECT SUM(preco_unitario * quantidade * (1 + iva/100))
        INTO valor
        FROM produtoencomenda 
        WHERE id_encomenda = id_enc;

        SELECT nome
        INTO nome_cliente
        FROM cliente
        WHERE id_cliente = id_cli;

        DBMS_OUTPUT.PUT_LINE('Encomenda ' || id_enc || ' do cliente ' || nome_cliente || ' no valor de ' || TRUNC(valor, 2) || ' euros registada a ' || TO_CHAR(data_registo, 'DD/MM/YYYY') || '.');

        DBMS_OUTPUT.PUT_LINE('Entregue a ' || TO_CHAR(data_entrega, 'DD/MM/YYYY') || '.');

        DBMS_OUTPUT.PUT_LINE('Estado: ENTREGUE.');

        FETCH encomendas INTO id_enc, id_cli, data_entrega, data_registo;
      END LOOP;
    END IF;

    CLOSE encomendas;
  END listar_encomendas_entregues;

  PROCEDURE listar_encomendas_pagas IS
    CURSOR encomendas IS
      SELECT id_encomenda, id_cliente, data_entrega, data_pagamento, data_registo
      FROM encomenda
      WHERE data_pagamento IS NOT NULL
      ORDER BY id_encomenda;
    id_enc ENCOMENDA.id_encomenda%TYPE;
    id_cli ENCOMENDA.id_cliente%TYPE;
    nome_cliente CLIENTE.nome%TYPE;
    valor FLOAT;
    data_entrega ENCOMENDA.data_entrega%TYPE;
    data_pagamento ENCOMENDA.data_pagamento%TYPE;
    data_registo ENCOMENDA.data_registo%TYPE;
  BEGIN
    OPEN encomendas;
    FETCH encomendas INTO id_enc, id_cli, data_entrega, data_pagamento, data_registo;

    IF encomendas%ROWCOUNT = 0 THEN
      DBMS_OUTPUT.PUT_LINE('Não existem encomendas pagas.');
    ELSE
      DBMS_OUTPUT.PUT_LINE('Encomendas PAGAS:');
      DBMS_OUTPUT.PUT_LINE('');

      LOOP
        EXIT WHEN encomendas%NOTFOUND;

        DBMS_OUTPUT.PUT_LINE('----');

        SELECT SUM(preco_unitario * quantidade * (1 + iva/100))
        INTO valor
        FROM produtoencomenda 
        WHERE id_encomenda = id_enc;

        SELECT nome
        INTO nome_cliente
        FROM cliente
        WHERE id_cliente = id_cli;

        DBMS_OUTPUT.PUT_LINE('Encomenda ' || id_enc || ' do cliente ' || nome_cliente || ' no valor de ' || TRUNC(valor, 2) || ' euros registada a ' || TO_CHAR(data_registo, 'DD/MM/YYYY') || '.');

        IF data_entrega IS NOT NULL THEN
          DBMS_OUTPUT.PUT_LINE('Entregue a ' || TO_CHAR(data_entrega, 'DD/MM/YYYY') || '.');
        END IF;

        DBMS_OUTPUT.PUT_LINE('Pagamento registado a ' || TO_CHAR(data_pagamento, 'DD/MM/YYYY') || '.');

        DBMS_OUTPUT.PUT_LINE('Estado: PAGA.');

        FETCH encomendas INTO id_enc, id_cli, data_entrega, data_pagamento, data_registo;
      END LOOP;
    END IF;

    CLOSE encomendas;
  END listar_encomendas_pagas;
END gestao_encomendas;