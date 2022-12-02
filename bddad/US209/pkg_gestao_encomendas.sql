CREATE OR REPLACE PACKAGE gestao_encomendas AS
  TYPE produtos IS TABLE OF NUMBER INDEX BY BINARY_INTEGER;

  FUNCTION registar_encomenda(id_cliente CLIENTE.id_cliente%TYPE, 
    lista_produtos produtos,
    data_registo ENCOMENDA.data_registo%TYPE) 
  RETURN ENCOMENDA.id_encomenda%TYPE;
  
  FUNCTION registar_encomenda(id_cliente CLIENTE.id_cliente%TYPE, 
    lista_produtos produtos, 
    morada_entrega ENCOMENDA.morada_entrega%TYPE, 
    cod_postal_entrega ENCOMENDA.cod_postal_entrega%TYPE,
    data_registo ENCOMENDA.data_registo%TYPE)
  RETURN ENCOMENDA.id_encomenda%TYPE;
  
  PROCEDURE registar_entrega(id_encomenda ENCOMENDA.id_encomenda%TYPE, 
    data_entrega ENCOMENDA.data_entrega%TYPE);
  
  PROCEDURE registar_pagamento(id_encomenda ENCOMENDA.id_encomenda%TYPE, 
    data_pagamento ENCOMENDA.data_pagamento%TYPE);

  PROCEDURE listar_encomendas;
  
  PROCEDURE listar_encomendas_registadas;
  
  PROCEDURE listar_encomendas_entregues;
  
  PROCEDURE listar_encomendas_pagas;

  encomenda_inexistente EXCEPTION;
  cliente_inexistente EXCEPTION;
  produto_inexistente EXCEPTION;
  sem_plafond EXCEPTION;
END gestao_encomendas;