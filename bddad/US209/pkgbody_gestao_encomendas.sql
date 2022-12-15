CREATE OR REPLACE PACKAGE BODY gestao_encomendas AS
  FUNCTION fn_registar_encomenda(id_cliente CLIENTE.id_cliente%TYPE, 
    lista_produtos produtos,
    data_registo ENCOMENDA.data_registo%TYPE) 
  RETURN ENCOMENDA.id_encomenda%TYPE IS
    encomenda_id ENCOMENDA.id_encomenda%TYPE;
    produto_id PRODUTO.id_produto%TYPE;
    cliente_id CLIENTE.id_cliente%TYPE;
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

    cliente_id := id_cliente;

    SELECT morada_entrega, cod_postal_entrega, plafond 
    INTO morada_entrega, cod_postal_entrega, plafond_cliente 
    FROM cliente 
    WHERE id_cliente = cliente_id;
  
    IF morada_entrega IS NULL THEN
      RAISE cliente_inexistente;
    END IF;

    SELECT MAX(id_encomenda) INTO encomenda_id FROM encomenda;

    IF encomenda_id IS NULL THEN
      encomenda_id := 1;
    ELSE
      encomenda_id := encomenda_id + 1;
    END IF;

    INSERT INTO encomenda (id_encomenda, id_cliente, data_vencimento_pagamento, data_registo, morada_entrega, cod_postal_entrega)
    VALUES (encomenda_id, id_cliente, data_registo + 30, data_registo, morada_entrega, cod_postal_entrega);

    registar_logs.pr_RegistarInsert(USER, sysdate,'Encomenda');

    valor_encomenda := 0;

    produto_id := lista_produtos.FIRST;

    WHILE produto_id IS NOT NULL LOOP
      SELECT preco, designacao 
      INTO preco_produto, designacao_produto 
      FROM produto 
      WHERE id_produto = produto_id;
  
      IF preco_produto IS NULL THEN
        RAISE produto_inexistente;
      END IF;
  
      SELECT valor
      INTO iva_produto
      FROM escalaoiva e, produto p
      WHERE p.id_produto = produto_id AND e.id_escalao_iva = p.id_escalao_iva;

      INSERT INTO produtoencomenda (id_encomenda, id_produto, quantidade, preco_unitario, iva, designacao_produto)
      VALUES (encomenda_id, produto_id, lista_produtos(produto_id), preco_produto, iva_produto, designacao_produto);

      registar_logs.pr_RegistarInsert(USER, sysdate,'ProdutoEncomenda');

      produto_id := lista_produtos.NEXT(produto_id);
    END LOOP;

    SELECT SUM(preco_unitario * quantidade * (1 + iva/100))
    INTO valor_por_pagar
    FROM produtoencomenda 
    WHERE id_encomenda IN (SELECT id_encomenda FROM encomenda WHERE id_cliente = cliente_id AND data_pagamento IS NULL);

    IF valor_por_pagar > plafond_cliente THEN
      RAISE sem_plafond;
    END IF;

    DBMS_OUTPUT.PUT_LINE('Encomenda ' || encomenda_id || ' registada com sucesso.');

    COMMIT;
    RETURN encomenda_id;
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
  END fn_registar_encomenda;

  FUNCTION fn_registar_encomenda(id_cliente CLIENTE.id_cliente%TYPE, 
    lista_produtos produtos, 
    morada_entrega ENCOMENDA.morada_entrega%TYPE, 
    cod_postal_entrega ENCOMENDA.cod_postal_entrega%TYPE,
    data_registo ENCOMENDA.data_registo%TYPE) 
  RETURN ENCOMENDA.id_encomenda%TYPE IS
    encomenda_id ENCOMENDA.id_encomenda%TYPE;
    produto_id PRODUTO.id_produto%TYPE;
    cliente_id CLIENTE.id_cliente%TYPE;
    preco_produto PRODUTO.preco%TYPE;
    iva_produto ESCALAOIVA.valor%TYPE;
    designacao_produto PRODUTO.designacao%TYPE;
    plafond_cliente CLIENTE.plafond%TYPE;
    valor_encomenda NUMBER;
    valor_por_pagar NUMBER;
  BEGIN
    SAVEPOINT inicio;

    cliente_id := id_cliente;

    SELECT plafond 
    INTO plafond_cliente 
    FROM cliente 
    WHERE id_cliente = cliente_id;
  
    IF plafond_cliente IS NULL THEN
      RAISE cliente_inexistente;
    END IF;

    SELECT MAX(id_encomenda) INTO encomenda_id FROM encomenda;

    IF encomenda_id IS NULL THEN
      encomenda_id := 1;
    ELSE
      encomenda_id := encomenda_id + 1;
    END IF;

    INSERT INTO encomenda (id_encomenda, id_cliente, data_vencimento_pagamento, data_registo, morada_entrega, cod_postal_entrega)
    VALUES (encomenda_id, id_cliente, data_registo + 30, data_registo, morada_entrega, cod_postal_entrega);

    registar_logs.pr_RegistarInsert(USER, sysdate,'Encomenda');

    valor_encomenda := 0;

    produto_id := lista_produtos.FIRST;

    WHILE produto_id IS NOT NULL LOOP
      SELECT preco, designacao 
      INTO preco_produto, designacao_produto 
      FROM produto
      WHERE id_produto = produto_id;
  
      IF preco_produto IS NULL THEN
        RAISE produto_inexistente;
      END IF;
  
      SELECT valor
      INTO iva_produto
      FROM escalaoiva e, produto p
      WHERE p.id_produto = produto_id AND e.id_escalao_iva = p.id_escalao_iva;

      INSERT INTO produtoencomenda (id_encomenda, id_produto, quantidade, preco_unitario, iva, designacao_produto)
      VALUES (encomenda_id, produto_id, lista_produtos(produto_id), preco_produto, iva_produto, designacao_produto);

      registar_logs.pr_RegistarInsert(USER, sysdate,'ProdutoEncomenda');

      produto_id := lista_produtos.NEXT(produto_id);
    END LOOP;

    SELECT SUM(preco_unitario * quantidade * (1 + iva/100))
    INTO valor_por_pagar
    FROM produtoencomenda 
    WHERE id_encomenda IN (SELECT id_encomenda FROM encomenda WHERE id_cliente = cliente_id AND data_pagamento IS NULL);

    IF valor_por_pagar > plafond_cliente THEN
      RAISE sem_plafond;
    END IF;

    DBMS_OUTPUT.PUT_LINE('Encomenda ' || encomenda_id || ' registada com sucesso.');

    COMMIT;
    RETURN encomenda_id;
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
  END fn_registar_encomenda;

  PROCEDURE pr_registar_entrega(id_encomenda ENCOMENDA.id_encomenda%TYPE, 
    data_entrega ENCOMENDA.data_entrega%TYPE) IS
    cont NUMBER;
    encomenda_id ENCOMENDA.id_encomenda%TYPE;
    data_ent ENCOMENDA.data_entrega%TYPE;
  BEGIN
    SAVEPOINT inicio;

    encomenda_id := id_encomenda;
    data_ent := data_entrega;

    SELECT COUNT(*) 
    INTO cont
    FROM encomenda
    WHERE id_encomenda = encomenda_id AND data_entrega IS NULL;

    IF cont = 0 THEN
      RAISE encomenda_inexistente;
    END IF;

    UPDATE encomenda
    SET (data_entrega) = data_ent
    WHERE id_encomenda = encomenda_id;

    DBMS_OUTPUT.PUT_LINE('Entrega da encomenda ' || encomenda_id || ' registada com sucesso.');

    registar_logs.pr_RegistarUpdate(USER, sysdate,'Encomenda');

    COMMIT;
  EXCEPTION
    WHEN encomenda_inexistente THEN
      RAISE_APPLICATION_ERROR(-20004, 'Encomenda inexistente.');
      ROLLBACK TO inicio;
    WHEN OTHERS THEN
      RAISE_APPLICATION_ERROR(-20005, 'Erro ao registar entrega.');
      ROLLBACK TO inicio;
  END pr_registar_entrega;

  PROCEDURE pr_registar_pagamento(id_encomenda ENCOMENDA.id_encomenda%TYPE, 
    data_pagamento ENCOMENDA.data_pagamento%TYPE) IS
    cont NUMBER;
    encomenda_id ENCOMENDA.id_encomenda%TYPE;
    data_pag ENCOMENDA.data_pagamento%TYPE;
  BEGIN
    SAVEPOINT inicio;

    encomenda_id := id_encomenda;
    data_pag := data_pagamento;

    SELECT COUNT(*) 
    INTO cont
    FROM encomenda
    WHERE id_encomenda = encomenda_id AND data_pagamento IS NULL;

    IF cont = 0 THEN
      RAISE encomenda_inexistente;
    END IF;

    UPDATE encomenda
    SET (data_pagamento) = data_pag
    WHERE id_encomenda = encomenda_id;

    DBMS_OUTPUT.PUT_LINE('Pagamento da encomenda ' || encomenda_id || ' registado com sucesso.');

    registar_logs.pr_RegistarUpdate(USER, sysdate,'Encomenda');

    COMMIT;
  EXCEPTION
    WHEN encomenda_inexistente THEN
      RAISE_APPLICATION_ERROR(-20004, 'Encomenda inexistente.');
      ROLLBACK TO inicio;
    WHEN OTHERS THEN
      RAISE_APPLICATION_ERROR(-20005, 'Erro ao registar pagamento.');
      ROLLBACK TO inicio;
  END pr_registar_pagamento;

  PROCEDURE pr_listar_encomendas IS
    CURSOR encomendas IS
      SELECT id_encomenda, id_cliente, data_entrega, data_pagamento, data_registo
      FROM encomenda
      ORDER BY id_encomenda;
    encomenda_id ENCOMENDA.id_encomenda%TYPE;
    cliente_id ENCOMENDA.id_cliente%TYPE;
    nome_cliente CLIENTE.nome%TYPE;
    valor FLOAT;
    data_entrega ENCOMENDA.data_entrega%TYPE;
    data_pagamento ENCOMENDA.data_pagamento%TYPE;
    data_registo ENCOMENDA.data_registo%TYPE;
  BEGIN
    OPEN encomendas;
    FETCH encomendas INTO encomenda_id, cliente_id, data_entrega, data_pagamento, data_registo;
  
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
        WHERE id_encomenda = encomenda_id;

        SELECT nome
        INTO nome_cliente
        FROM cliente
        WHERE id_cliente = cliente_id;

        DBMS_OUTPUT.PUT_LINE('Encomenda ' || encomenda_id || ' do cliente ' || nome_cliente || ' no valor de ' || TRUNC(valor, 2) || ' euros registada a ' || TO_CHAR(data_registo, 'DD/MM/YYYY') || '.');

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

        FETCH encomendas INTO encomenda_id, cliente_id, data_entrega, data_pagamento, data_registo;
      END LOOP;
    END IF;

    CLOSE encomendas;
  END pr_listar_encomendas;

  PROCEDURE pr_listar_encomendas_registadas IS
    CURSOR encomendas IS
      SELECT id_encomenda, id_cliente, data_registo
      FROM encomenda
      WHERE data_entrega IS NULL AND data_pagamento IS NULL
      ORDER BY id_encomenda;
    encomenda_id ENCOMENDA.id_encomenda%TYPE;
    cliente_id ENCOMENDA.id_cliente%TYPE;
    nome_cliente CLIENTE.nome%TYPE;
    valor FLOAT;
    data_registo ENCOMENDA.data_registo%TYPE;
  BEGIN
    OPEN encomendas;
    FETCH encomendas INTO encomenda_id, cliente_id, data_registo;

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
        WHERE id_encomenda = encomenda_id;

        SELECT nome
        INTO nome_cliente
        FROM cliente
        WHERE id_cliente = cliente_id;

        DBMS_OUTPUT.PUT_LINE('Encomenda ' || encomenda_id || ' do cliente ' || nome_cliente || ' no valor de ' || TRUNC(valor, 2) || ' euros registada a ' || TO_CHAR(data_registo, 'DD/MM/YYYY') || '.');

        DBMS_OUTPUT.PUT_LINE('Estado: REGISTADA.');

        FETCH encomendas INTO encomenda_id, cliente_id, data_registo;
      END LOOP;
    END IF;

    CLOSE encomendas;
  END pr_listar_encomendas_registadas;

  PROCEDURE pr_listar_encomendas_entregues IS
    CURSOR encomendas IS
      SELECT id_encomenda, id_cliente, data_entrega, data_registo, data_pagamento
      FROM encomenda
      WHERE data_entrega IS NOT NULL
      ORDER BY id_encomenda;
    encomenda_id ENCOMENDA.id_encomenda%TYPE;
    cliente_id ENCOMENDA.id_cliente%TYPE;
    nome_cliente CLIENTE.nome%TYPE;
    data_entrega ENCOMENDA.data_entrega%TYPE;
    data_registo ENCOMENDA.data_registo%TYPE;
    data_pagamento ENCOMENDA.data_pagamento%TYPE;
    valor FLOAT;
  BEGIN
    OPEN encomendas;
    FETCH encomendas INTO encomenda_id, cliente_id, data_entrega, data_registo, data_pagamento;

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
        WHERE id_encomenda = encomenda_id;

        SELECT nome
        INTO nome_cliente
        FROM cliente
        WHERE id_cliente = cliente_id;

        DBMS_OUTPUT.PUT_LINE('Encomenda ' || encomenda_id || ' do cliente ' || nome_cliente || ' no valor de ' || TRUNC(valor, 2) || ' euros registada a ' || TO_CHAR(data_registo, 'DD/MM/YYYY') || '.');

        DBMS_OUTPUT.PUT_LINE('Entregue a ' || TO_CHAR(data_entrega, 'DD/MM/YYYY') || '.');

        IF data_pagamento IS NOT NULL THEN
          DBMS_OUTPUT.PUT_LINE('Pagamento registado a ' || TO_CHAR(data_pagamento, 'DD/MM/YYYY') || '.');
        END IF;

        IF data_pagamento IS NOT NULL THEN
          DBMS_OUTPUT.PUT_LINE('Estado: PAGA.');
        ELSE
          DBMS_OUTPUT.PUT_LINE('Estado: ENTREGUE.');
        END IF;

        FETCH encomendas INTO encomenda_id, cliente_id, data_entrega, data_registo, data_pagamento;
      END LOOP;
    END IF;

    CLOSE encomendas;
  END pr_listar_encomendas_entregues;

  PROCEDURE pr_listar_encomendas_pagas IS
    CURSOR encomendas IS
      SELECT id_encomenda, id_cliente, data_entrega, data_pagamento, data_registo
      FROM encomenda
      WHERE data_pagamento IS NOT NULL
      ORDER BY id_encomenda;
    encomenda_id ENCOMENDA.id_encomenda%TYPE;
    cliente_id ENCOMENDA.id_cliente%TYPE;
    nome_cliente CLIENTE.nome%TYPE;
    valor FLOAT;
    data_entrega ENCOMENDA.data_entrega%TYPE;
    data_pagamento ENCOMENDA.data_pagamento%TYPE;
    data_registo ENCOMENDA.data_registo%TYPE;
  BEGIN
    OPEN encomendas;
    FETCH encomendas INTO encomenda_id, cliente_id, data_entrega, data_pagamento, data_registo;

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
        WHERE id_encomenda = encomenda_id;

        SELECT nome
        INTO nome_cliente
        FROM cliente
        WHERE id_cliente = cliente_id;

        DBMS_OUTPUT.PUT_LINE('Encomenda ' || encomenda_id || ' do cliente ' || nome_cliente || ' no valor de ' || TRUNC(valor, 2) || ' euros registada a ' || TO_CHAR(data_registo, 'DD/MM/YYYY') || '.');

        IF data_entrega IS NOT NULL THEN
          DBMS_OUTPUT.PUT_LINE('Entregue a ' || TO_CHAR(data_entrega, 'DD/MM/YYYY') || '.');
        END IF;

        DBMS_OUTPUT.PUT_LINE('Pagamento registado a ' || TO_CHAR(data_pagamento, 'DD/MM/YYYY') || '.');

        DBMS_OUTPUT.PUT_LINE('Estado: PAGA.');

        FETCH encomendas INTO encomenda_id, cliente_id, data_entrega, data_pagamento, data_registo;
      END LOOP;
    END IF;

    CLOSE encomendas;
  END pr_listar_encomendas_pagas;
END gestao_encomendas;