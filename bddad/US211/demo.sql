DECLARE
TYPE produtos IS TABLE OF NUMBER INDEX BY BINARY_INTEGER;

operacao_id Operacao.id_operacao%TYPE;
id_escalao_iva ESCALAOIVA.id_escalao_iva%TYPE;

lista_produtos produtos;
id_banana Produto.id_produto%TYPE;
id_maca Produto.id_produto%TYPE;

BEGIN
DELETE FROM produto;
DELETE FROM escalaoIva;

INSERT INTO escalaoiva (id_escalao_iva, valor)
VALUES (1, 6)
RETURNING id_escalao_iva INTO id_escalao_iva;

INSERT INTO produto (id_produto, designacao, preco, id_escalao_iva)
VALUES (1, 'Banana da Madeira', 0.50, id_escalao_iva)
RETURNING id_produto INTO id_banana;

INSERT INTO produto (id_produto, designacao, preco, id_escalao_iva)
VALUES (2, 'Maçã de Alcobaça', 1.00, id_escalao_iva)
RETURNING id_produto INTO id_maca;

operacoes.cancel_operacao(3);
operacoes.cancel_operacao(4);

operacoes.atualizar_operacao_datas(3,'01-Jan-2023');


END;

