CREATE OR REPLACE PACKAGE operacoes AS

  -- Procedimento para cancelar operacao
  PROCEDURE cancel_operacao (operacao_id Operacao.id_operacao%TYPE);

  -- Procedimento para atualizar operacoes com datas
  PROCEDURE atualizar_operacao_datas (operacao_id Operacao.id_operacao%TYPE, data_nova TIMESTAMP);

  -- Procedimento para atualizar operacoes com produtos
  PROCEDURE atualizar_operacao_produtos (operacao_id Operacao.id_operacao%TYPE, quantidade_nova Colheita.quantidade%TYPE);

  -- Procedimento para atualizar operacoes fator aplicacao
  PROCEDURE atualizar_operacao_fator_aplicacao (operacao_id Operacao.id_operacao%TYPE, quantidade_nova FatorProducaoAplicacao.quantidade%TYPE);

  -- Procedimento para atualizar operacoes aplicacao
  PROCEDURE atualizar_operacao_tipo_aplicacao (operacao_id Operacao.id_operacao%TYPE, tipo_aplicacao_id Aplicacao.id_tipo_aplicacao%TYPE, novo_tipo_aplicacao TipoAplicacao.tipo_aplicacao%TYPE);

END operacoes;
