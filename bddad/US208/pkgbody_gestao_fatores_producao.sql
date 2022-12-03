  CREATE OR REPLACE PACKAGE BODY gestao_fatores_producao AS


    FUNCTION fn_RegistarTipoFatorProducao(designacao TIPOFATORPRODUCAO.tipo_fator_producao%TYPE) 
    RETURN TIPOFATORPRODUCAO.id_tipo_fator_producao%TYPE AS
      new_id TIPOFATORPRODUCAO.id_tipo_fator_producao%TYPE;

    BEGIN
      SAVEPOINT inicio;

      SELECT MAX(id_tipo_fator_producao) INTO new_id 
      FROM TIPOFATORPRODUCAO;
      IF new_id IS NULL THEN
        new_id := 0;
      END IF;

      new_id := new_id + 1;

      INSERT INTO TIPOFATORPRODUCAO(id_tipo_fator_producao, tipo_fator_producao)
      VAlUES (new_id, designacao);

      DBMS_OUTPUT.PUT_LINE('TIPO FATOR PRODUCAO ' || new_id || ' registado com sucesso.');

      RETURN new_id;

      EXCEPTION
      WHEN OTHERS THEN
      RAISE_APPLICATION_ERROR(-20005, 'Erro ao registar tipo fator produção.');
        ROLLBACK TO inicio;

    END fn_RegistarTipoFatorProducao;





    FUNCTION fn_RegistarCategoriaSubstancia(designacao CATEGORIASUBSTANCIA.categoria_substancia%TYPE) 
    RETURN CATEGORIASUBSTANCIA.id_categoria_substancia%TYPE AS
      new_id CATEGORIASUBSTANCIA.id_categoria_substancia%TYPE;

    BEGIN
      SAVEPOINT inicio;

      SELECT MAX(id_categoria_substancia) INTO new_id 
      FROM CATEGORIASUBSTANCIA;
      IF new_id IS NULL THEN
        new_id := 0;
      END IF;

      new_id := new_id + 1;

      INSERT INTO CATEGORIASUBSTANCIA(id_categoria_substancia, categoria_substancia)
      VAlUES (new_id, designacao);

      DBMS_OUTPUT.PUT_LINE('CATEGORIA SUBSTANCIA ' || new_id || ' registado com sucesso.');

      RETURN new_id;

      EXCEPTION
      WHEN OTHERS THEN
      RAISE_APPLICATION_ERROR(-20005, 'Erro ao registar categoria substância.');
        ROLLBACK TO inicio;

    END fn_RegistarCategoriaSubstancia;



    FUNCTION fn_RegistarFornecedor(designacao FORNECEDOR.fornecedor%TYPE) 
    RETURN FORNECEDOR.id_fornecedor%TYPE AS
      new_id FORNECEDOR.id_fornecedor%TYPE;

    BEGIN
      SAVEPOINT inicio;

      SELECT MAX(id_fornecedor) INTO new_id 
      FROM FORNECEDOR;
      IF new_id IS NULL THEN
        new_id := 0;
      END IF;

      new_id := new_id + 1;

      INSERT INTO FORNECEDOR(id_fornecedor, fornecedor)
      VAlUES (new_id, designacao);

      DBMS_OUTPUT.PUT_LINE('FORNECEDOR ' || new_id || ' registado com sucesso.');

      RETURN new_id;

      EXCEPTION
      WHEN OTHERS THEN
      RAISE_APPLICATION_ERROR(-20005, 'Erro ao registar fornecedor.');
        ROLLBACK TO inicio;

    END fn_RegistarFornecedor;


    FUNCTION fn_RegistarTipoFormulacao(designacao TIPOFORMULACAO.tipo_formulacao%TYPE) 
    RETURN TIPOFORMULACAO.id_tipo_formulacao%TYPE AS
      new_id TIPOFORMULACAO.id_tipo_formulacao%TYPE;

    BEGIN
      SAVEPOINT inicio;

      SELECT MAX(id_tipo_formulacao) INTO new_id 
      FROM TIPOFORMULACAO;
      IF new_id IS NULL THEN
        new_id := 0;
      END IF;

      new_id := new_id + 1;

      INSERT INTO TIPOFORMULACAO(id_tipo_formulacao, tipo_formulacao)
      VAlUES (new_id, designacao);

      DBMS_OUTPUT.PUT_LINE('TIPO FORMULACAO ' || new_id || ' registado com sucesso.');

      RETURN new_id;

      EXCEPTION
      WHEN OTHERS THEN
      RAISE_APPLICATION_ERROR(-20005, 'Erro ao registar tipo formulação.');
        ROLLBACK TO inicio;

    END fn_RegistarTipoFormulacao;


    FUNCTION fn_RegistarSubstancia(designacao SUBSTANCIA.substancia%TYPE,
      forn SUBSTANCIA.id_fornecedor%TYPE,
      cat_sub SUBSTANCIA.id_categoria_substancia%TYPE) 
    RETURN SUBSTANCIA.id_substancia%TYPE AS
      new_id SUBSTANCIA.id_substancia%TYPE;
      flag NUMBER;

    BEGIN
      SAVEPOINT inicio;

      /* check if tipo fator producao is already registered */
      SELECT COUNT(*) INTO flag 
      FROM FORNECEDOR
      WHERE id_fornecedor = forn;
      IF flag = 0 THEN
        RAISE fornecedor_inexistente;
      END IF;

      /* check if tipo categoria de substancia is already registered */
      SELECT COUNT(*) INTO flag 
      FROM CATEGORIASUBSTANCIA
      WHERE id_categoria_substancia = cat_sub;
      IF flag = 0 THEN
        RAISE categoria_substancia_inexistente;
      END IF;

      SELECT MAX(id_substancia) INTO new_id 
      FROM SUBSTANCIA;
      IF new_id IS NULL THEN
        new_id := 0;
      END IF;

      new_id := new_id + 1;

      INSERT INTO SUBSTANCIA(id_substancia, substancia, id_fornecedor, id_categoria_substancia)
      VAlUES (new_id, designacao, forn, cat_sub);

      DBMS_OUTPUT.PUT_LINE('SUBSTANCIA ' || new_id || ' registado com sucesso.');

      RETURN new_id;

      EXCEPTION
      WHEN fornecedor_inexistente THEN
        RAISE_APPLICATION_ERROR(-20001, 'Fornecedor inexistente.');
      WHEN categoria_substancia_inexistente THEN
        RAISE_APPLICATION_ERROR(-20001, 'Categoria Substancia inexistente.');
      WHEN OTHERS THEN
      RAISE_APPLICATION_ERROR(-20005, 'Erro ao registar substancia.');
        ROLLBACK TO inicio;

    END fn_RegistarSubstancia;





     FUNCTION fn_RegistarFatorProducao(id_tipo_fator FATORPRODUCAO.id_tipo_fator_producao%TYPE,
      designacao FATORPRODUCAO.nome%TYPE,
      form FATORPRODUCAO.id_tipo_formulacao%TYPE) 
    RETURN FATORPRODUCAO.id_fator_producao%TYPE AS
      new_id FATORPRODUCAO.id_fator_producao%TYPE;
      flag NUMBER;

    BEGIN
      SAVEPOINT inicio;

      /* check if there is any registered culture */
      SELECT MAX(id_fator_producao) INTO new_id 
      FROM FATORPRODUCAO;
      IF new_id IS NULL THEN
        new_id := 0;
      END IF;
      new_id := new_id + 1;

      /* check if tipo fator producao is already registered */
      SELECT COUNT(*) INTO flag 
      FROM TIPOFATORPRODUCAO
      WHERE id_tipo_fator_producao = id_tipo_fator;
      IF flag = 0 THEN
        RAISE tipo_fator_producao_inexistente;
      END IF;

      /* check if tipo formulacao is already registered */
      SELECT COUNT(*) INTO flag 
      FROM TIPOFORMULACAO
      WHERE id_tipo_formulacao = form;
      IF flag = 0 THEN
        RAISE tipo_formulacao_inexistente;
      END IF;


      INSERT INTO FATORPRODUCAO(id_fator_producao, id_tipo_fator_producao, nome, id_tipo_formulacao)
      VAlUES (new_id, id_tipo_fator, designacao, form);

      DBMS_OUTPUT.PUT_LINE('FATOR PRODUCAO ' || new_id || ' registado com sucesso.');

      RETURN new_id;

      EXCEPTION
      WHEN tipo_fator_producao_inexistente THEN
        RAISE_APPLICATION_ERROR(-20001, 'Tipo Fator de Produção inexistente.');
      WHEN tipo_formulacao_inexistente THEN
        RAISE_APPLICATION_ERROR(-20001, 'Tipo Formulação inexistente.');
      WHEN OTHERS THEN
      RAISE_APPLICATION_ERROR(-20005, 'Erro ao registar fator producao.');
        ROLLBACK TO inicio;

    END fn_RegistarFatorProducao;




    PROCEDURE pr_RegistarFatorProducaoSubstancia(fat_prod FATORPRODUCAOSUBSTANCIA.id_fator_producao%TYPE,
      subs FATORPRODUCAOSUBSTANCIA.id_substancia%TYPE,
      quantidade FATORPRODUCAOSUBSTANCIA.quantidade%TYPE,
      unidade FATORPRODUCAOSUBSTANCIA.unidade%TYPE) AS
      flag NUMBER;

    BEGIN
      SAVEPOINT inicio;


      /* check if tipo fator producao is already registered */
      SELECT COUNT(*) INTO flag 
      FROM FATORPRODUCAO
      WHERE id_fator_producao = fat_prod;
      IF flag = 0 THEN
        RAISE fator_producao_inexistente;
      END IF;

      /* check if tipo formulacao is already registered */
      SELECT COUNT(*) INTO flag 
      FROM SUBSTANCIA
      WHERE id_substancia = subs;
      IF flag = 0 THEN
        RAISE substancia_inexistente;
      END IF;

      /* check if quantidade is valid */
      if quantidade <= 0 THEN
        RAISE quantidade_invalida;
      END IF;

      INSERT INTO FATORPRODUCAOSUBSTANCIA(id_fator_producao, id_substancia, quantidade, unidade)
      VAlUES (fat_prod, subs, quantidade, unidade);

      DBMS_OUTPUT.PUT_LINE('FATOR PRODUCAO - SUBSTANCIA: registado com sucesso.');


      EXCEPTION
      WHEN fator_producao_inexistente THEN
        RAISE_APPLICATION_ERROR(-20001, 'Fator de Produção inexistente.');
      WHEN substancia_inexistente THEN
        RAISE_APPLICATION_ERROR(-20001, 'Substância inexistente.');
      WHEN quantidade_invalida THEN
        RAISE_APPLICATION_ERROR(-20001, 'Quantidade inválida. Deve ser positiva.');
      WHEN OTHERS THEN
      RAISE_APPLICATION_ERROR(-20005, 'Erro ao registar fator producao substancia.');
        ROLLBACK TO inicio;

    END pr_RegistarFatorProducaoSubstancia;

  END gestao_fatores_producao; 