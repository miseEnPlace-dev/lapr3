DECLARE

  id_fator_producao FATORPRODUCAO.id_fator_producao%TYPE;
  id_tipo_fator_producao TIPOFATORPRODUCAO.id_tipo_fator_producao%TYPE;
  id_substancia SUBSTANCIA.id_substancia%TYPE;
  id_tipo_formulacao TIPOFORMULACAO.id_tipo_formulacao%TYPE;
  id_categoria_substancia CATEGORIASUBSTANCIA.id_categoria_substancia%TYPE;
  id_fornecedor FORNECEDOR.id_fornecedor%TYPE;

BEGIN

  DELETE FROM TIPOFATORPRODUCAO;
  DELETE FROM TIPOFORMULACAO;
  DELETE FROM SUBSTANCIA;
  DELETE FROM FATORPRODUCAO;
  DELETE FROM CATEGORIASUBSTANCIA;
  DELETE FROM SUBSTANCIA;

  id_tipo_fator_producao := gestao_fatores_producao.fn_RegistarTipoFatorProducao('tipo fator producao');  
  id_tipo_formulacao := gestao_fatores_producao.fn_RegistarTipoFormulacao('tipo formulacao');  
  id_categoria_substancia := gestao_fatores_producao.fn_RegistarCategoriaSubstancia('categoria substancia');  
  id_fornecedor := gestao_fatores_producao.fn_RegistarFornecedor('fornecedor');  
  id_substancia := gestao_fatores_producao.fn_RegistarSubstancia('substancia', id_fornecedor, id_categoria_substancia);  
  id_fator_producao := gestao_fatores_producao.fn_RegistarFatorProducao(id_tipo_fator_producao, 'fator producao', id_tipo_formulacao);  
  
  gestao_fatores_producao.pr_RegistarFatorProducaoSubstancia(id_fator_producao, id_substancia, 10, 'mg');


  /* Test Exceptions */

  DBMS_OUTPUT.PUT_LINE('Expected fornecedor_inexistente');
  id_substancia := gestao_fatores_producao.fn_RegistarSubstancia('substancia', 99, id_categoria_substancia); 
  
  DBMS_OUTPUT.PUT_LINE('Expected categoria_substancia_inexistente');
  id_substancia := gestao_fatores_producao.fn_RegistarSubstancia('substancia', id_fornecedor, 99); 
  
  DBMS_OUTPUT.PUT_LINE('Expected tipo_fator_producao_inexistente');
  id_fator_producao := gestao_fatores_producao.fn_RegistarFatorProducao(99, 'fator producao', id_tipo_formulacao);  

  DBMS_OUTPUT.PUT_LINE('Expected tipo_formulacao_inexistente');
  id_fator_producao := gestao_fatores_producao.fn_RegistarFatorProducao(id_tipo_fator_producao, 'fator producao', 99);  

  DBMS_OUTPUT.PUT_LINE('Expected fator_producao_inexistente');
  gestao_fatores_producao.pr_RegistarFatorProducaoSubstancia(99, id_substancia, 10, 'mg');

  DBMS_OUTPUT.PUT_LINE('Expected substancia_inexistente');
  gestao_fatores_producao.pr_RegistarFatorProducaoSubstancia(id_fator_producao, 99, 10, 'mg');





END;