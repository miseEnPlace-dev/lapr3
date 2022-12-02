CREATE OR REPLACE PACKAGE BODY rentabilidade AS
  PROCEDURE listarSetoresPorQtdProducao IS
    CURSOR setores IS
      SELECT s.id_setor, s.designacao, s.area, SUM(c.quantidade) AS qtd_producao
      FROM Setor s
      INNER JOIN Colheita c
        ON s.id_setor = c.id_setor
      GROUP BY s.id_setor, s.designacao, s.area
      ORDER BY SUM(c.quantidade) / s.area DESC;
    v_id_setor SETOR.id_setor%TYPE;
    v_designacao SETOR.designacao%TYPE;
    v_area SETOR.area%TYPE;
    v_qtd_producao COLHEITA.quantidade%TYPE;
    resultado SYS_REFCURSOR;
  BEGIN
    OPEN setores;
    FETCH setores INTO v_id_setor, v_designacao, v_area, v_qtd_producao;

    IF setores%NOTFOUND THEN
      DBMS_OUTPUT.PUT_LINE('Não existem setores com colheitas efetuadas.');
    ELSE
      DBMS_OUTPUT.PUT_LINE('Setores por ordem de Quantidade de Produção:');

      LOOP
        EXIT WHEN setores%NOTFOUND;

        DBMS_OUTPUT.PUT_LINE('---');

        DBMS_OUTPUT.PUT_LINE('Setor ' || v_designacao || ' com id ' || v_id_setor);
        DBMS_OUTPUT.PUT_LINE('Área: ' || v_area || ' ha');
        DBMS_OUTPUT.PUT_LINE('Quantidade de produção: ' || TRUNC(v_qtd_producao / v_area, 4) || ' toneladas/ha');

        FETCH setores INTO v_id_setor, v_designacao, v_area, v_qtd_producao;

      END LOOP;
    END IF;

    CLOSE setores;

  END listarSetoresPorQtdProducao;

  -- PROCEDURE listarSetoresPorLucro IS
  -- END listarSetoresPorLucro;
END rentabilidade;
