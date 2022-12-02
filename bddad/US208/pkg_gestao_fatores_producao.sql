CREATE OR REPLACE PACKAGE gestao_fatores_producao AS

  FUNCTION fn_RegistarTipoFatorProducao(designacao TIPOFATORPRODUCAO.tipo_fator_producao%TYPE) 
  RETURN TIPOFATORPRODUCAO.id_tipo_fator_producao%TYPE;

  FUNCTION fn_RegistarTipoFormulacao(designacao TIPOFORMULACAO.tipo_formulacao%TYPE) 
  RETURN TIPOFORMULACAO.id_tipo_formulacao%TYPE;

  FUNCTION fn_RegistarSubstancia(designacao SUBSTANCIA.substancia%TYPE) 
  RETURN SUBSTANCIA.id_substancia%TYPE;

  FUNCTION fn_RegistarFatorProducao(id_tipo_fator FATORPRODUCAO.id_tipo_fator_producao%TYPE,
    designacao FATORPRODUCAO.nome%TYPE,
    tipo_formulacao FATORPRODUCAO.id_tipo_formulacao%TYPE) 
  RETURN FATORPRODUCAO.id_fator_producao%TYPE;


  PROCEDURE pr_RegistarFatorProducaoSubstancia(fator_producao FATORPRODUCAOSUBSTANCIA.id_fator_producao%TYPE,
    substancia FATORPRODUCAOSUBSTANCIA.id_substancia%TYPE,
    percentagem FATORPRODUCAOSUBSTANCIA.percentagem%TYPE);

    
  tipo_fator_producao_inexistente EXCEPTION;
  tipo_formulacao_inexistente EXCEPTION;
  fator_producao_inexistente EXCEPTION;
  substancia_inexistente EXCEPTION;
  percentagem_invalida EXCEPTION;
  

END gestao_fatores_producao;