DECLARE
operacao_id_3 Operacao.id_operacao%TYPE;
operacao_id_4 Operacao.id_operacao%TYPE;
operacao_id_5 Operacao.id_operacao%TYPE;
id_escalao_iva ESCALAOIVA.id_escalao_iva%TYPE;
data_prevista_operacao3 Operacao.data_prevista_operacao%TYPE;
data_operacao3 Operacao.data_operacao%TYPE;
data_prevista_operacao4 Operacao.data_prevista_operacao%TYPE;
data_operacao4 Operacao.data_operacao%TYPE;
quantidade_pera Colheita.quantidade%TYPE;
quantidade_maca Colheita.quantidade%TYPE;

quantidade_nova_colheita Colheita.quantidade%TYPE;

quantidade_fator_producao FatorProducaoAplicacao.quantidade%TYPE;
quantidade_nova_fator_producao FatorProducaoAplicacao.quantidade%TYPE;

tipo_aplicacao_id Aplicacao.id_tipo_aplicacao%TYPE;
novo_tipo_aplicacao TipoAplicacao.tipo_aplicacao%TYPE;
tipo_aplicacao TipoAplicacao.tipo_aplicacao%TYPE;

id_pera Produto.id_produto%TYPE;
id_maca Produto.id_produto%TYPE;

BEGIN
SELECT id_produto INTO id_maca FROM produto WHERE id_produto = 1;
SELECT id_produto INTO id_pera FROM produto WHERE id_produto = 2;

operacoes.cancel_operacao(3);

-- Print dados da operecao 3

SELECT id_operacao, data_prevista_operacao, data_operacao INTO operacao_id_3, data_prevista_operacao3, data_operacao3 FROM operacao WHERE id_operacao = 3;

DBMS_OUTPUT.PUT_LINE('Operação 3:');
DBMS_OUTPUT.PUT_LINE(chr(13));
DBMS_OUTPUT.PUT_LINE('ID: ' || operacao_id_3);
DBMS_OUTPUT.PUT_LINE('Data Prevista: ' || data_prevista_operacao3);
DBMS_OUTPUT.PUT_LINE('Data: ' || data_operacao3);

operacoes.atualizar_operacao_datas(4,'01-Jan-2023');

-- Print dados da operecao 4 com datas atualizadas

SELECT id_operacao, data_prevista_operacao, data_operacao INTO operacao_id_4, data_prevista_operacao4, data_operacao4 FROM operacao WHERE id_operacao = 4;

DBMS_OUTPUT.PUT_LINE(chr(13));
DBMS_OUTPUT.PUT_LINE('Operação 4 atualizada com datas novas:');
DBMS_OUTPUT.PUT_LINE(chr(13));
DBMS_OUTPUT.PUT_LINE('ID: ' || operacao_id_4);
DBMS_OUTPUT.PUT_LINE('Data Prevista: ' || data_prevista_operacao4);
DBMS_OUTPUT.PUT_LINE('Data: ' || data_operacao4);


quantidade_nova_colheita := 200;

operacoes.atualizar_operacao_produtos(4, quantidade_nova_colheita);

-- Print dados da operecao 4 com quantidade de produtos atualizada

SELECT quantidade INTO quantidade_maca FROM colheita WHERE id_operacao = 4 AND id_produto = id_maca;

DBMS_OUTPUT.PUT_LINE(chr(13));
DBMS_OUTPUT.PUT_LINE('Operação 4 atualizada com quantidade de produtos:');
DBMS_OUTPUT.PUT_LINE(chr(13));
DBMS_OUTPUT.PUT_LINE('ID: ' || operacao_id_4);
DBMS_OUTPUT.PUT_LINE('Data Prevista: ' || data_prevista_operacao4);
DBMS_OUTPUT.PUT_LINE('Data: ' || data_operacao4);
DBMS_OUTPUT.PUT_LINE('Quantidade de maçãs: ' || quantidade_maca);

quantidade_nova_fator_producao := 111;

operacoes.atualizar_operacao_fator_aplicacao(5, quantidade_nova_fator_producao);

-- Print dados da operecao 5 com quantidade de fator de producao atualizada

SELECT quantidade INTO quantidade_fator_producao FROM fatorproducaoaplicacao WHERE id_operacao = 5;
SELECT id_tipo_aplicacao INTO tipo_aplicacao_id FROM aplicacao WHERE id_operacao = 5;
SELECT tipo_aplicacao INTO tipo_aplicacao FROM tipoaplicacao WHERE id_tipo_aplicacao = tipo_aplicacao_id;

DBMS_OUTPUT.PUT_LINE(chr(13));
DBMS_OUTPUT.PUT_LINE('Operação 5 atualizada com quantidade de fator de produção:');
DBMS_OUTPUT.PUT_LINE(chr(13));
DBMS_OUTPUT.PUT_LINE('ID: ' || operacao_id_5);
DBMS_OUTPUT.PUT_LINE('ID tipo aplicacao: ' || tipo_aplicacao_id);
DBMS_OUTPUT.PUT_LINE('Quantidade de fator de produção: ' || quantidade_fator_producao);
DBMS_OUTPUT.PUT_LINE('Tipo de aplicação: ' || tipo_aplicacao);

novo_tipo_aplicacao := 'Foliar';

operacoes.atualizar_operacao_tipo_aplicacao(5, 1, novo_tipo_aplicacao);

-- Print dados da operecao 5 com tipo de aplicacao atualizada

SELECT id_tipo_aplicacao INTO tipo_aplicacao_id FROM aplicacao WHERE id_operacao = 5;
SELECT tipo_aplicacao INTO tipo_aplicacao FROM tipoaplicacao WHERE id_tipo_aplicacao = tipo_aplicacao_id;


DBMS_OUTPUT.PUT_LINE(chr(13));
DBMS_OUTPUT.PUT_LINE('Operação 5 atualizada com tipo de aplicação:');
DBMS_OUTPUT.PUT_LINE(chr(13));
DBMS_OUTPUT.PUT_LINE('ID: ' || operacao_id_5);
DBMS_OUTPUT.PUT_LINE('ID tipo aplicacao: ' || tipo_aplicacao_id);
DBMS_OUTPUT.PUT_LINE('Quantidade de fator de produção: ' || quantidade_fator_producao);
DBMS_OUTPUT.PUT_LINE('Tipo de aplicação: ' || tipo_aplicacao);


END;

