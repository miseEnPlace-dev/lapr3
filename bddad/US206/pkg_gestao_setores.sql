CREATE OR REPLACE PACKAGE gestao_setores AS

  FUNCTION registar_setor(designacao SETOR.designacao%TYPE, 
    area SETOR.area%TYPE) 
  RETURN id_setor SETOR.id_setor%TYPE;

END gestao_setores;