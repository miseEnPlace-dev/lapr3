CREATE OR REPLACE PACKAGE BODY gestao_setores AS

  FUNCTION registar_setor(designacao SETOR.designacao%TYPE, 
    area SETOR.area%TYPE) 
    RETURN SETOR.id_setor%TYPE AS
    new_id SETOR.id_setor%TYPE;

  BEGIN
    SAVEPOINT inicio;

    SELECT MAX(id_setor) INTO new_id 
    FROM SETOR;

    IF new_id IS NULL THEN
      new_id := 0;
    END IF;

    new_id := new_id + 1;

    INSERT INTO SETOR(id_setor, designacao, area)
    VAlUES (new_id, designacao, area);

    DBMS_OUTPUT.PUT_LINE('SETOR ' || new_id || ' registado com sucesso.');

    RETURN new_id;

  END registar_setor;



  FUNCTION registar_tipo_cultura(tipo_cultura TIPOCULTURA.tipo_cultura%TYPE)  
    RETURN TIPOCULTURA.id_tipo_cultura%TYPE AS
    new_id TIPOCULTURA.id_tipo_cultura%TYPE;

  BEGIN
    SAVEPOINT inicio;

    SELECT MAX(id_tipo_cultura) INTO new_id 
    FROM TIPOCULTURA;

    IF new_id IS NULL THEN
      new_id := 0;
    END IF;

    new_id := new_id + 1;

    INSERT INTO TIPOCULTURA(id_tipo_cultura, tipo_cultura)
    VAlUES (new_id, tipo_cultura);

    DBMS_OUTPUT.PUT_LINE('TIPOCULTURA ' || new_id || ' registado com sucesso.');

    RETURN new_id;

  END registar_tipo_cultura;




  FUNCTION registar_cultura(cultura_param CULTURA.cultura%TYPE,
    id_tipo_cultura CULTURA.id_tipo_cultura%TYPE,
    id_produto CULTURA.id_produto%TYPE)
    RETURN CULTURA.id_cultura%TYPE AS
    new_id CULTURA.id_cultura%TYPE;
    flag NUMBER;


  BEGIN
    SAVEPOINT inicio;

    /* check if there is any registered culture */
    SELECT MAX(id_cultura) INTO new_id 
    FROM CULTURA;
    IF new_id IS NULL THEN
      new_id := 0;
    END IF;
    new_id := new_id + 1;

    /* check if culture type is already registered */
    SELECT COUNT(*) INTO flag 
    FROM TIPOCULTURA
    WHERE id_tipo_cultura = id_tipo_cultura;
    IF flag = 0 THEN
      RAISE tipo_cultura_inexistente;
    END IF;

    /* check if product type is already registered */
    SELECT COUNT(*) INTO flag 
    FROM PRODUTO
    WHERE id_produto = id_produto;
    IF flag = 0 THEN
      RAISE produto_inexistente;
    END IF;


    INSERT INTO CULTURA(id_cultura, cultura, id_tipo_cultura, id_produto)
    VAlUES (new_id, cultura_param, id_tipo_cultura, id_produto);

    DBMS_OUTPUT.PUT_LINE('CULTURA ' || new_id || ' registado com sucesso.');

    RETURN new_id;

    EXCEPTION
    WHEN tipo_cultura_inexistente THEN
      RAISE_APPLICATION_ERROR(-20001, 'Tipo cultura inexistente.');
      ROLLBACK TO inicio;
    WHEN produto_inexistente THEN
      RAISE_APPLICATION_ERROR(-20002, 'Produto inexistente.');
      ROLLBACK TO inicio;
    WHEN OTHERS THEN
      RAISE_APPLICATION_ERROR(-20005, 'Erro ao registar entrega.');
      ROLLBACK TO inicio;
  END registar_cultura;



END gestao_setores;



DECLARE
    id_setor NUMBER;
BEGIN
    id_setor := gestao_setores.registar_setor('designação', 300);
END;

DECLARE
    id_tipo_cultura NUMBER;
BEGIN
    id_tipo_cultura := gestao_setores.registar_tipo_cultura('tipo cultura');
END;

DECLARE
    id_cultura NUMBER;
BEGIN
    id_cultura := gestao_setores.registar_cultura('cultura', 1, 1);
END;
