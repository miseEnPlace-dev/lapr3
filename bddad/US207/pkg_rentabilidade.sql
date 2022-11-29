CREATE OR REPLACE PACKAGE rentabilidade AS
  FUNCTION calcular_rentabilidade(id_cliente CLIENTE.id_cliente%TYPE)
  RETURN NUMBER;

  rentabilidade_inexistente EXCEPTION;
END rentabilidade;
