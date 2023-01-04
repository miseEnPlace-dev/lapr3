CREATE OR REPLACE VIEW evolucaoProducaoUltimosCincoAnos AS
  SELECT p.tipo, t.ano, t.mes, SUM(pr.quantidade) AS quantidade
  FROM Producao pr
  INNER JOIN Produto p ON pr.id_produto = p.id_produto
  INNER JOIN Tempo t ON pr.id_tempo = t.id_tempo
  INNER JOIN Setor s ON pr.id_setor = s.id_setor
  WHERE t.ano >= TO_CHAR(SYSDATE - 5,'YYYY') AND t.ano <= TO_CHAR(SYSDATE,'YYYY')
  GROUP BY p.tipo, t.ano, t.mes
  ORDER BY t.ano, t.mes;

CREATE OR REPLACE VIEW compararVendasAnuais AS
  SELECT p.tipo, t.ano, SUM(pr.quantidade) AS quantidade
  FROM Venda ve
  INNER JOIN Produto p ON ve.id_produto = p.id_produto
  INNER JOIN Tempo t ON ve.id_tempo = t.id_tempo
  INNER JOIN Setor s ON ve.id_setor = s.id_setor
  INNER JOIN Producao pr ON ve.id_produto = pr.id_produto
  WHERE t.ano >= TO_CHAR(SYSDATE - 1,'YYYY') AND t.ano <= TO_CHAR(SYSDATE,'YYYY')
  GROUP BY p.tipo, t.ano
  ORDER BY t.ano;

CREATE OR REPLACE VIEW compararVendasMensaisPorTipoCultura AS
  SELECT p.tipo, t.mes, SUM(pr.quantidade) AS quantidade
  FROM Venda ve
  INNER JOIN Produto p ON ve.id_produto = p.id_produto
  INNER JOIN Tempo t ON ve.id_tempo = t.id_tempo
  INNER JOIN Setor s ON ve.id_setor = s.id_setor
  INNER JOIN Producao pr ON ve.id_produto = pr.id_produto
  WHERE t.mes >= TO_CHAR(SYSDATE - 1,'MM') AND t.mes <= TO_CHAR(SYSDATE,'MM')
  GROUP BY p.tipo, t.mes
  ORDER BY t.mes;
