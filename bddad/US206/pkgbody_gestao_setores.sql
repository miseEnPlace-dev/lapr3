CREATE OR REPLACE PACKAGE BODY gestao_setores AS

  FUNCTION registar_setor(designacao SETOR.designacao%TYPE, 
    area SETOR.area%TYPE) 
    RETURN SETOR.id_setor%TYPE AS
    last_id SETOR.id_setor%TYPE;

  BEGIN
    SAVEPOINT inicio;

    SELECT MAX(id_setor) INTO last_id 
    FROM SETOR;

    IF last_id IS NULL THEN
      last_id := 0;
    END IF;

    last_id := last_id + 1;

    INSERT INTO SETOR(id_setor, designacao, area)
    VAlUES (last_id, designacao, area);

    DBMS_OUTPUT.PUT_LINE('SETOR ' || last_id || ' registado com sucesso.');

    RETURN last_id;

  END registar_setor;

END gestao_setores;

DECLARE
    id_setor NUMBER;
BEGIN
    id_setor := gestao_setores.registar_setor('designação', 300);
END;
