CREATE OR REPLACE PACKAGE BODY rentabilidade AS
  FUNCTION listarSetoresPorQtdProducao()
  RETURN ListaSetores IS
    setores ListaSetores;
  BEGIN
    SAVEPOINT inicio;

    SELECT s.id_setor, s.designacao, sum(c.quantidade) AS qtd_producao
    FROM Setor s
    INNER JOIN Colheita c
        ON c.id_setor = s.id_setor
    GROUP BY s.id_setor, s.designacao
    ORDER BY qtd_producao DESC;

    RETURN setores;
  END GetSetores;
END rentabilidade;
