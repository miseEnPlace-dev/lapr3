CREATE OR REPLACE PACKAGE gestao_setores AS

  FUNCTION fn_RegistarSetor(designacao SETOR.designacao%TYPE, 
    area SETOR.area%TYPE) 
  RETURN SETOR.id_setor%TYPE;

  FUNCTION fn_RegistarTipoCultura(tipo_cultura TIPOCULTURA.tipo_cultura%TYPE) 
  RETURN TIPOCULTURA.id_tipo_cultura%TYPE;
  
  FUNCTION fn_RegistarCultura(cultura_param CULTURA.cultura%TYPE,
    id_t_cultura CULTURA.id_tipo_cultura%TYPE)
  RETURN CULTURA.id_cultura%TYPE;

  PROCEDURE pr_RegistarPlantacao(id_set PLANTACAO.id_setor%TYPE,
    id_cult PLANTACAO.id_cultura%TYPE,
    data_i PLANTACAO.data_inicio%TYPE);

  PROCEDURE pr_ListarSetoresOrdemAlfabetica;
  PROCEDURE pr_ListarSetoresTamanhoCrescente;
  PROCEDURE pr_ListarSetoresTamanhoDecrescente;
  PROCEDURE pr_ListarSetoresOrdemTipoCultura;
  PROCEDURE pr_ListarSetoresOrdemCultura;


  tipo_cultura_inexistente EXCEPTION;
  setor_inexistente EXCEPTION;
  cultura_inexistente EXCEPTION;

END gestao_setores;