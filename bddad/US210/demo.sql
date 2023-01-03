BEGIN
  gestao_operacoes.pr_listar_operacoes(1, TO_DATE('01/02/2021', 'DD/MM/YYYY'), TO_DATE('20/02/2021', 'DD/MM/YYYY'));
  DBMS_OUTPUT.PUT_LINE(chr(13)||chr(10));
  gestao_operacoes.pr_listar_operacoes(2, TO_DATE('01/02/2021', 'DD/MM/YYYY'), TO_DATE('20/02/2021', 'DD/MM/YYYY'));
  DBMS_OUTPUT.PUT_LINE(chr(13)||chr(10));
  gestao_operacoes.pr_listar_operacoes(3, TO_DATE('01/02/2021', 'DD/MM/YYYY'), TO_DATE('20/02/2021', 'DD/MM/YYYY'));
END;