CREATE OR REPLACE PACKAGE BODY gestao_setores AS

  FUNCTION fn_RegistarSetor(designacao SETOR.designacao%TYPE,
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

    registar_logs.pr_RegistarInsert(USER, sysdate,'Setor');

    RETURN new_id;

  END fn_RegistarSetor;



  FUNCTION fn_RegistarTipoCultura(tipo_cultura TIPOCULTURA.tipo_cultura%TYPE)
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

    registar_logs.pr_RegistarInsert(USER, sysdate,'TipoCultura');

    RETURN new_id;

  END fn_RegistarTipoCultura;




  FUNCTION fn_RegistarCultura(cultura_param CULTURA.cultura%TYPE,
    id_t_cultura CULTURA.id_tipo_cultura%TYPE)
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
    WHERE id_tipo_cultura = id_t_cultura;
    IF flag = 0 THEN
      RAISE tipo_cultura_inexistente;
    END IF;


    INSERT INTO CULTURA(id_cultura, cultura, id_tipo_cultura)
    VAlUES (new_id, cultura_param, id_t_cultura);

    registar_logs.pr_RegistarInsert(USER, sysdate,'Cultura');

    RETURN new_id;

    EXCEPTION
    WHEN tipo_cultura_inexistente THEN
      RAISE_APPLICATION_ERROR(-20001, 'Tipo cultura inexistente.');
    WHEN OTHERS THEN
      RAISE_APPLICATION_ERROR(-20005, 'Erro ao registar cultura.');
      ROLLBACK TO inicio;
  END fn_RegistarCultura;



  PROCEDURE pr_ListarSetoresOrdemAlfabetica IS
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
  END pr_ListarSetoresOrdemAlfabetica;



  PROCEDURE pr_ListarSetoresTamanhoCrescente IS
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
      DBMS_OUTPUT.PUT_LINE('Setores Ordem Tamanho Crescente:');
      DBMS_OUTPUT.PUT_LINE('');

      LOOP
        EXIT WHEN setores%NOTFOUND;

        DBMS_OUTPUT.PUT_LINE('id ' || setor_id || ': Setor ' || nome || ' tem area de ' || tamanho || '.');

        FETCH setores INTO setor_id, nome, tamanho;

      END LOOP;
    END IF;

    CLOSE setores;
  END pr_ListarSetoresTamanhoCrescente;



  PROCEDURE pr_ListarSetoresTamanhoDecrescente IS
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
      DBMS_OUTPUT.PUT_LINE('Setores Ordem Tamanho Decrescente:');
      DBMS_OUTPUT.PUT_LINE('');

      LOOP
        EXIT WHEN setores%NOTFOUND;

        DBMS_OUTPUT.PUT_LINE('id ' || setor_id || ': Setor ' || nome || ' tem area de ' || tamanho || '.');

        FETCH setores INTO setor_id, nome, tamanho;

      END LOOP;
    END IF;

    CLOSE setores;
  END pr_ListarSetoresTamanhoDecrescente;


  PROCEDURE pr_ListarSetoresOrdemTipoCultura IS
    CURSOR setores IS
      SELECT s.id_setor, s.designacao, s.area, c.cultura, tc.tipo_cultura
      FROM setor s, plantacao p, cultura c, TipoCultura tc
      WHERE p.id_cultura = c.id_cultura
        AND tc.id_tipo_cultura = c.id_tipo_cultura
        AND s.id_setor = (
                    SELECT pl.id_setor
                      FROM Plantacao pl
                    ORDER BY data_inicio DESC
                    FETCH FIRST 1 ROWS ONLY)
      ORDER BY tc.tipo_cultura, c.cultura;
    setor_id SETOR.id_setor%TYPE;
    nome SETOR.designacao%TYPE;
    tamanho SETOR.area%TYPE;
    cultura_designacao CULTURA.cultura%TYPE;
    t_culura TIPOCULTURA.tipo_cultura%TYPE;
  BEGIN
    OPEN setores;
    FETCH setores INTO setor_id, nome, tamanho, cultura_designacao, t_culura;

    IF setores%NOTFOUND THEN
      DBMS_OUTPUT.PUT_LINE('Não existem setores entregues.');
    ELSE
      DBMS_OUTPUT.PUT_LINE('Setores Ordem Alfabética Tipo Cultura:');
      DBMS_OUTPUT.PUT_LINE('');

      LOOP
        EXIT WHEN setores%NOTFOUND;

        DBMS_OUTPUT.PUT_LINE('id ' || setor_id || ': Setor ' || nome || ' tem area de ' || tamanho || '. Cultura: ' || cultura_designacao || ' , tipo: ' || t_culura);

        FETCH setores INTO setor_id, nome, tamanho, cultura_designacao, t_culura;

      END LOOP;
    END IF;

    CLOSE setores;
  END pr_ListarSetoresOrdemTipoCultura;




  PROCEDURE pr_ListarSetoresOrdemCultura IS
    CURSOR setores IS
      SELECT s.id_setor, s.designacao, s.area, c.cultura
      FROM setor s, plantacao p, cultura c
      WHERE p.id_cultura = c.id_cultura
        AND s.id_setor = (
                    SELECT pl.id_setor
                      FROM Plantacao pl
                    ORDER BY data_inicio DESC
                    FETCH FIRST 1 ROWS ONLY)
      ORDER BY c.cultura;
    setor_id SETOR.id_setor%TYPE;
    nome SETOR.designacao%TYPE;
    tamanho SETOR.area%TYPE;
    cultura_designacao CULTURA.cultura%TYPE;
  BEGIN
    OPEN setores;
    FETCH setores INTO setor_id, nome, tamanho, cultura_designacao;

    IF setores%NOTFOUND THEN
      DBMS_OUTPUT.PUT_LINE('Não existem setores entregues.');
    ELSE
      DBMS_OUTPUT.PUT_LINE('Setores Ordem Alfabética Cultura:');
      DBMS_OUTPUT.PUT_LINE('');

      LOOP
        EXIT WHEN setores%NOTFOUND;

        DBMS_OUTPUT.PUT_LINE('id ' || setor_id || ': Setor ' || nome || ' tem area de ' || tamanho || '. Cultura: ' || cultura_designacao || '.');

        FETCH setores INTO setor_id, nome, tamanho, cultura_designacao;

      END LOOP;
    END IF;

    CLOSE setores;
  END pr_ListarSetoresOrdemCultura;



  PROCEDURE pr_RegistarPlantacao(id_set PLANTACAO.id_setor%TYPE,
    id_cult PLANTACAO.id_cultura%TYPE,
    data_i PLANTACAO.data_inicio%TYPE) AS
      flag NUMBER;

    BEGIN
      SAVEPOINT inicio;


      /* check if tipo fator producao is already registered */
      SELECT COUNT(*) INTO flag
      FROM SETOR
      WHERE id_setor = id_set;
      IF flag = 0 THEN
        RAISE setor_inexistente;
      END IF;

      /* check if tipo fator producao is already registered */
      SELECT COUNT(*) INTO flag
      FROM CULTURA
      WHERE id_cultura = id_cult;
      IF flag = 0 THEN
        RAISE cultura_inexistente;
      END IF;

      INSERT INTO PLANTACAO(id_setor, id_cultura, data_inicio)
      VAlUES (id_set, id_cult, data_i);

      DBMS_OUTPUT.PUT_LINE('PLANTACAO registado com sucesso.');

      registar_logs.pr_RegistarInsert(USER, sysdate,'Plantacao');

      EXCEPTION
      WHEN setor_inexistente THEN
        RAISE_APPLICATION_ERROR(-20001, 'Setor inexistente.');
      WHEN cultura_inexistente THEN
        RAISE_APPLICATION_ERROR(-20001, 'Cultura inexistente.');
      WHEN OTHERS THEN
      RAISE_APPLICATION_ERROR(-20005, 'Erro ao registar plantacao.');
        ROLLBACK TO inicio;

    END pr_RegistarPlantacao;


END gestao_setores;
