CREATE OR REPLACE VIEW evolucaoVendaMensaisPorTipoCulturaHub AS
  SELECT p.tipo, t.ano, t.mes, SUM(pr.quantidade) AS quantidade
  FROM Venda ve
  INNER JOIN Produto p ON ve.id_produto = p.id_produto
  INNER JOIN Tempo t ON ve.id_tempo = t.id_tempo
  INNER JOIN Setor s ON ve.id_setor = s.id_setor
  INNER JOIN Hub h ON ve.id_hub = h.id_hub
  WHERE t.ano >= TO_CHAR(SYSDATE - 1,'MM') AND t.ano <= TO_CHAR(SYSDATE,'MM')
  GROUP BY p.tipo, t.ano, t.mes
  ORDER BY t.ano, t.mes;
