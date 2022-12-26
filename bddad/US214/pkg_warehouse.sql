CREATE OR REPLACE PACKAGE warehouse AS
    FUNCTION evolucaoProducao (prod Produto.designacao%TYPE, ano Tempo.ano%TYPE, mes Tempo.mes%TYPE, setor Producao.id_setor%TYPE ) RETURN NUMBER;

END warehouse;
