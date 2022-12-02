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

  END fn_RegistarTipoFatorProducao;





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

  END fn_RegistarTipoFormulacao;




  FUNCTION fn_RegistarSubstancia(designacao SUBSTANCIA.substancia%TYPE) 
  RETURN SUBSTANCIA.id_substancia%TYPE AS
    new_id SUBSTANCIA.id_substancia%TYPE;

  BEGIN
    SAVEPOINT inicio;

    SELECT MAX(id_substancia) INTO new_id 
    FROM SUBSTANCIA;
    IF new_id IS NULL THEN
      new_id := 0;
    END IF;

    new_id := new_id + 1;

    INSERT INTO SUBSTANCIA(id_substancia, substancia)
    VAlUES (new_id, designacao);

    DBMS_OUTPUT.PUT_LINE('SUBSTANCIA ' || new_id || ' registado com sucesso.');

    RETURN new_id;

  END fn_RegistarSubstancia;





  FUNCTION fn_RegistarFatorProducao(id_tipo_fator FATORPRODUCAO.id_tipo_fator_producao%TYPE,
    designacao FATORPRODUCAO.nome%TYPE,
    tipo_formulacao FATORPRODUCAO.id_tipo_formulacao%TYPE) 
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
    WHERE id_tipo_formulacao = tipo_formulacao;
    IF flag = 0 THEN
      RAISE tipo_formulacao_inexistente;
    END IF;


    INSERT INTO FATORPRODUCAO(id_fator_producao, id_tipo_fator_producao, nome, id_tipo_formulacao)
    VAlUES (new_id, id_tipo_fator, designacao, tipo_formulacao);

    DBMS_OUTPUT.PUT_LINE('FATOR PRODUCAO ' || new_id || ' registado com sucesso.');

    RETURN new_id;

    EXCEPTION
    WHEN tipo_fator_producao_inexistente THEN
      RAISE_APPLICATION_ERROR(-20001, 'Tipo Fator de Produção inexistente.');
    WHEN tipo_formulacao_inexistente THEN
      RAISE_APPLICATION_ERROR(-20001, 'Tipo Formulação inexistente.');
    WHEN OTHERS THEN
    RAISE_APPLICATION_ERROR(-20005, 'Erro ao registar entrega.');
      ROLLBACK TO inicio;

  END fn_RegistarFatorProducao;




  PROCEDURE pr_RegistarFatorProducaoSubstancia(fator_producao FATORPRODUCAOSUBSTANCIA.id_fator_producao%TYPE,
    substancia FATORPRODUCAOSUBSTANCIA.id_substancia%TYPE,
    percentagem FATORPRODUCAOSUBSTANCIA.percentagem%TYPE) AS
    flag NUMBER;

  BEGIN
    SAVEPOINT inicio;


    /* check if tipo fator producao is already registered */
    SELECT COUNT(*) INTO flag 
    FROM FATORPRODUCAO
    WHERE id_fator_producao = fator_producao;
    IF flag = 0 THEN
      RAISE fator_producao_inexistente;
    END IF;

    /* check if tipo formulacao is already registered */
    SELECT COUNT(*) INTO flag 
    FROM SUBSTANCIA
    WHERE id_substancia = substancia;
    IF flag = 0 THEN
      RAISE substancia_inexistente;
    END IF;

    /* check if percentagem is valid */
    if percentagem <= 0 OR percentagem > 100 THEN
      RAISE percentagem_invalida;
    END IF;

    INSERT INTO FATORPRODUCAOSUBSTANCIA(id_fator_producao, id_substancia, percentagem)
    VAlUES (fator_producao, substancia, percentagem);

    DBMS_OUTPUT.PUT_LINE('FATOR PRODUCAO - SUBSTANCIA: registado com sucesso.');


    EXCEPTION
    WHEN fator_producao_inexistente THEN
      RAISE_APPLICATION_ERROR(-20001, 'Fator de Produção inexistente.');
    WHEN substancia_inexistente THEN
      RAISE_APPLICATION_ERROR(-20001, 'Substância inexistente.');
    WHEN percentagem_invalida THEN
      RAISE_APPLICATION_ERROR(-20001, 'Percentagem inválida.');
    WHEN OTHERS THEN
    RAISE_APPLICATION_ERROR(-20005, 'Erro ao registar entrega.');
      ROLLBACK TO inicio;

  END pr_RegistarFatorProducaoSubstancia;

END gestao_fatores_producao; 