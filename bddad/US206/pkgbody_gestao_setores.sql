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
    id_tipo_cultura CULTURA.id_tipo_cultura%TYPE)
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


    INSERT INTO CULTURA(id_cultura, cultura, id_tipo_cultura)
    VAlUES (new_id, cultura_param, id_tipo_cultura);

    DBMS_OUTPUT.PUT_LINE('CULTURA ' || new_id || ' registado com sucesso.');

    RETURN new_id;

    EXCEPTION
    WHEN tipo_cultura_inexistente THEN
      RAISE_APPLICATION_ERROR(-20001, 'Tipo cultura inexistente.');
    WHEN OTHERS THEN
      RAISE_APPLICATION_ERROR(-20005, 'Erro ao registar entrega.');
      ROLLBACK TO inicio;
  END registar_cultura;



  PROCEDURE listar_setores_ordem_alfabetica IS
    CURSOR setores IS
      SELECT id_setor, designacao, area
      FROM SETOR
      ORDER BY designacao;
    setor_id SETOR.id_setor%TYPE;
    nome SETOR.designacao%TYPE;
    tamanho SETOR.area%TYPE;
  BEGIN
    OPEN setores;
    FETCH setores INTO setor_id, nome, tamanho;

    IF setores%NOTFOUND THEN
      DBMS_OUTPUT.PUT_LINE('Não existem setores entregues.');
    ELSE
      DBMS_OUTPUT.PUT_LINE('Setores Ordem Alfabética:');
      DBMS_OUTPUT.PUT_LINE('');

      LOOP
        EXIT WHEN setores%NOTFOUND;

        DBMS_OUTPUT.PUT_LINE('id ' || setor_id || ': Setor ' || nome || ' tem area de ' || tamanho || '.');

        FETCH setores INTO setor_id, nome, tamanho;

      END LOOP;
    END IF;

    CLOSE setores;
  END listar_setores_ordem_alfabetica;



  PROCEDURE listar_setores_tamanho_crescente IS
    CURSOR setores IS
      SELECT id_setor, designacao, area
      FROM SETOR
      ORDER BY area ASC;
    setor_id SETOR.id_setor%TYPE;
    nome SETOR.designacao%TYPE;
    tamanho SETOR.area%TYPE;
  BEGIN
    OPEN setores;
    FETCH setores INTO setor_id, nome, tamanho;

    IF setores%NOTFOUND THEN
      DBMS_OUTPUT.PUT_LINE('Não existem setores entregues.');
    ELSE
      DBMS_OUTPUT.PUT_LINE('Setores Ordem Alfabética:');
      DBMS_OUTPUT.PUT_LINE('');

      LOOP
        EXIT WHEN setores%NOTFOUND;

        DBMS_OUTPUT.PUT_LINE('id ' || setor_id || ': Setor ' || nome || ' tem area de ' || tamanho || '.');

        FETCH setores INTO setor_id, nome, tamanho;

      END LOOP;
    END IF;

    CLOSE setores;
  END listar_setores_tamanho_crescente;



  PROCEDURE listar_setores_tamanho_decrescente IS
    CURSOR setores IS
      SELECT id_setor, designacao, area
      FROM SETOR
      ORDER BY area DESC;
    setor_id SETOR.id_setor%TYPE;
    nome SETOR.designacao%TYPE;
    tamanho SETOR.area%TYPE;
  BEGIN
    OPEN setores;
    FETCH setores INTO setor_id, nome, tamanho;

    IF setores%NOTFOUND THEN
      DBMS_OUTPUT.PUT_LINE('Não existem setores entregues.');
    ELSE
      DBMS_OUTPUT.PUT_LINE('Setores Ordem Alfabética:');
      DBMS_OUTPUT.PUT_LINE('');

      LOOP
        EXIT WHEN setores%NOTFOUND;

        DBMS_OUTPUT.PUT_LINE('id ' || setor_id || ': Setor ' || nome || ' tem area de ' || tamanho || '.');

        FETCH setores INTO setor_id, nome, tamanho;

      END LOOP;
    END IF;

    CLOSE setores;
  END listar_setores_tamanho_decrescente;



END gestao_setores;
