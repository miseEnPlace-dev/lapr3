CREATE OR REPLACE VIEW evolucaoVendaMensaisPorTipoCulturaHub AS
  SELECT p.tipo, s.id_setor, t.mes, SUM(ve.quantidade) AS quantidade, h.id_hub AS hub
  FROM Venda ve
  INNER JOIN Produto p ON ve.id_produto = p.id_produto
  INNER JOIN Tempo t ON ve.id_tempo = t.id_tempo
  INNER JOIN Setor s ON ve.id_setor = s.id_setor
  INNER JOIN Hub h ON ve.id_hub = h.id_hub
  WHERE t.mes >= (SELECT MIN(mes) FROM Tempo)
  GROUP BY p.tipo, s.id_setor, t.mes, h.id_hub
  ORDER BY s.id_setor, t.mes;
