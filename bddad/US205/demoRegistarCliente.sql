DECLARE
id_cliente CLIENTE.id_cliente%TYPE;

BEGIN
DELETE FROM Cliente;

id_cliente := gestao_clientes.fn_RegistarCliente('João', 123456789, 'email@email.com', 'Rua do João', 'Rua do João', 100, '1234-567', '1234-567');

END;
