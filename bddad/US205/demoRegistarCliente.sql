DECLARE
  id_cliente CLIENTE.id_cliente%TYPE;
BEGIN
  DELETE FROM Cliente;
  -- success
  id_cliente := gestao_clientes.fn_RegistarCliente('João', 123456789, 'email@email.com', 'Rua do João', 'Rua do João', '1234-567', '1234-567', 100);
  -- error in cod_postal_entrega
  id_cliente := gestao_clientes.fn_RegistarCliente('João', 123456789, 'email@email.com', 'Rua do João', 'Rua do João', '1234-567', '1111-111', 100);
  -- error in cod_postal
  id_cliente := gestao_clientes.fn_RegistarCliente('João', 123456789, 'email@email.com', 'Rua do João', 'Rua do João', '1111-111', '1234-567', 100);
  -- success in client with id = 2
  id_cliente := gestao_clientes.fn_RegistarCliente('João', 123456789, 'email@email.com', 'Rua do João', 'Rua do João', '1234-567', '1234-567', 100);
END;

