CREATE OR REPLACE PACKAGE operacoes AS
  -- Cria tabela temporária
  TYPE produtos IS TABLE OF NUMBER INDEX BY BINARY_INTEGER;

  -- Procedimento para cancelar operacao
  PROCEDURE cancel_operacao (operacao_id Operacao.id_operacao%TYPE);

  -- Procedimento para atualizar operacoes com datas
  PROCEDURE atualizar_operacao_datas (operacao_id Operacao.id_operacao%TYPE, data_nova TIMESTAMP);

  -- Procedimento para atualizar operacoes com produtos
  PROCEDURE atualizar_operacao_produtos (operacao_id Operacao.id_operacao%TYPE, lista_produtos produtos);

END operacoes;
