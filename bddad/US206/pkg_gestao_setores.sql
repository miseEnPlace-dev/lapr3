CREATE OR REPLACE PACKAGE gestao_setores AS

  FUNCTION registar_setor(designacao SETOR.designacao%TYPE, 
    area SETOR.area%TYPE) 
  RETURN SETOR.id_setor%TYPE;

  FUNCTION registar_tipo_cultura(tipo_cultura TIPOCULTURA.tipo_cultura%TYPE) 
  RETURN TIPOCULTURA.id_tipo_cultura%TYPE;
  
  FUNCTION registar_cultura(cultura_param CULTURA.cultura%TYPE,
    id_tipo_cultura CULTURA.id_tipo_cultura%TYPE)
  RETURN CULTURA.id_cultura%TYPE;

  tipo_cultura_inexistente EXCEPTION;

END gestao_setores;