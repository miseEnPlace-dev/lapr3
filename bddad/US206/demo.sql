DECLARE
  id_setor1 SETOR.id_setor%TYPE;
  id_setor2 SETOR.id_setor%TYPE;
  id_setor3 SETOR.id_setor%TYPE;
  id_tipo_cultura1 TIPOCULTURA.id_tipo_cultura%TYPE;
  id_tipo_cultura2 TIPOCULTURA.id_tipo_cultura%TYPE;
  id_tipo_cultura3 TIPOCULTURA.id_tipo_cultura%TYPE;
  id_cultura1 CULTURA.id_cultura%TYPE;
  id_cultura2 CULTURA.id_cultura%TYPE;
  id_cultura3 CULTURA.id_cultura%TYPE;
BEGIN
  DELETE FROM SETOR;
  DELETE FROM CULTURA;
  DELETE FROM TIPOCULTURA;

  id_setor1 := gestao_setores.fn_RegistarSetor('Setor D', 200);
  id_setor2 := gestao_setores.fn_RegistarSetor('Setor A', 100);
  id_setor3 := gestao_setores.fn_RegistarSetor('Setor C', 300);
  id_tipo_cultura1 := gestao_setores.fn_RegistarTipoCultura('Tipo 2');
  id_tipo_cultura2 := gestao_setores.fn_RegistarTipoCultura('Tipo 7');
  id_tipo_cultura3 := gestao_setores.fn_RegistarTipoCultura('Tipo 5');
  id_cultura1 := gestao_setores.fn_RegistarCultura('Cultura 4', id_tipo_cultura1);
  id_cultura2 := gestao_setores.fn_RegistarCultura('Cultura 5', id_tipo_cultura2);
  id_cultura3 := gestao_setores.fn_RegistarCultura('Cultura 1', id_tipo_cultura3);
  gestao_setores.pr_RegistarPlantacao(id_setor1, id_cultura1, '01-Jan-2020 12:00:00');
  gestao_setores.pr_RegistarPlantacao(id_setor2, id_cultura2, '01-Jan-2020 12:00:00');
  gestao_setores.pr_RegistarPlantacao(id_setor3, id_cultura3, '01-Jan-2020 12:00:00');
  

  gestao_setores.pr_ListarSetoresOrdemAlfabetica;
  gestao_setores.pr_ListarSetoresTamanhoCrescente;
  gestao_setores.pr_ListarSetoresTamanhoDecrescente;
  gestao_setores.pr_ListarSetoresOrdemTipoCultura;
  gestao_setores.pr_ListarSetoresOrdemCultura;


  /* Test Exceptions */

  DBMS_OUTPUT.PUT_LINE('Expected tipo_cultura_inexistente ');
  id_cultura1 := gestao_setores.fn_RegistarCultura('Cultura 4', 99);
  
  DBMS_OUTPUT.PUT_LINE('Expected setor_inexistente');
  gestao_setores.pr_RegistarPlantacao(99, id_cultura1, '01-Jan-2020 12:00:00');

  
  DBMS_OUTPUT.PUT_LINE('Expected cultura_inexistente');
  gestao_setores.pr_RegistarPlantacao(id_setor1, 99, '01-Jan-2020 12:00:00');


END;