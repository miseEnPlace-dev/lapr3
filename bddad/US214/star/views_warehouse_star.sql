CREATE OR REPLACE VIEW evolucaoProducaoUltimosCincoAnos AS
  SELECT p.tipo, t.ano, t.mes, SUM(pr.quantidade) AS quantidade
  FROM Producao pr
  INNER JOIN Produto p ON pr.id_produto = p.id_produto
  INNER JOIN Tempo t ON pr.id_tempo = t.id_tempo
  INNER JOIN Setor s ON pr.id_setor = s.id_setor
  WHERE t.ano >= SYSDATE - 5 AND t.ano <= SYSDATE
  GROUP BY p.tipo, t.ano, t.mes;

