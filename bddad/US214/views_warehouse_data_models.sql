CREATE OR REPLACE VIEW evolucaoProducaoUltimosCincoAnosPorCulturaESetor AS
  SELECT p.tipo, s.id_setor, t.ano, SUM(pr.quantidade) AS quantidade
  FROM Producao pr
  INNER JOIN Produto p ON pr.id_produto = p.id_produto
  INNER JOIN Setor s ON pr.id_setor = s.id_setor
  INNER JOIN Tempo t ON pr.id_tempo = t.id_tempo
  WHERE t.ano >= (SELECT MAX(ano) FROM Tempo) - 5 AND t.ano <= (SELECT MAX(ano) FROM Tempo)
  GROUP BY p.tipo, s.id_setor, t.ano
  ORDER BY s.id_setor, t.ano;


CREATE OR REPLACE VIEW compararVendasAnuais AS
  SELECT p.tipo, s.id_setor, t.ano, SUM(pr.quantidade) AS quantidade
  FROM Venda ve
  INNER JOIN Produto p ON ve.id_produto = p.id_produto
  INNER JOIN Tempo t ON ve.id_tempo = t.id_tempo
  INNER JOIN Setor s ON ve.id_setor = s.id_setor
  INNER JOIN Producao pr ON ve.id_produto = pr.id_produto
   WHERE t.ano >= (SELECT MAX(ano) FROM Tempo) - 1 AND t.ano <= (SELECT MAX(ano) FROM Tempo)
  GROUP BY p.tipo, s.id_setor, t.ano
  ORDER BY s.id_setor, t.ano;

CREATE OR REPLACE VIEW compararVendasMensaisPorTipoCultura AS
  SELECT p.tipo, t.mes, SUM(pr.quantidade) AS quantidade
  FROM Venda ve
  INNER JOIN Produto p ON ve.id_produto = p.id_produto
  INNER JOIN Tempo t ON ve.id_tempo = t.id_tempo
  INNER JOIN Setor s ON ve.id_setor = s.id_setor
  INNER JOIN Producao pr ON ve.id_produto = pr.id_produto
  WHERE t.mes >= (SELECT MAX(mes) FROM Tempo) - 1 AND t.mes <= (SELECT MAX(mes) FROM Tempo)
  GROUP BY p.tipo, t.mes
  ORDER BY t.mes;
