DECLARE
operacao_id Operacao.id_operacao%TYPE;

BEGIN


operacoes.cancel_operacao(3);
operacoes.cancel_operacao(4);

operacoes.atualizar_operacao_datas(3,'01-Jan-2023');


END;

