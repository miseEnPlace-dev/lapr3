CREATE OR REPLACE PACKAGE rentabilidade AS
  TYPE ListaSetores IS TABLE OF Setor%ROWTYPE;
  FUNCTION listarSetoresPorQtdProducao RETURN ListaSetores;
END rentabilidade;
