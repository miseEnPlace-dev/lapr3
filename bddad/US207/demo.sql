BEGIN
    DELETE FROM Setor;
    DELETE FROM Colheita;
    DELETE FROM Produto;

    INSERT INTO Setor (id_setor,designacao,area) VALUES (1,'Setor 1',200);
    INSERT INTO Setor (id_setor,designacao,area) VALUES (2,'Setor 2',100);
    INSERT INTO Setor (id_setor,designacao,area) VALUES (3,'Setor 3',300);

    INSERT INTO Produto (id_produto,designacao,preco,id_escalao_iva) VALUES (1,'Maça',2,2);
    INSERT INTO Produto (id_produto,designacao,preco,id_escalao_iva) VALUES (2,'Pera',2,2);
    INSERT INTO Produto (id_produto,designacao,preco,id_escalao_iva) VALUES (3,'Laranja',2,2);
    INSERT INTO Produto (id_produto,designacao,preco,id_escalao_iva) VALUES (4,'Cerejeira',10,1);
    INSERT INTO Produto (id_produto,designacao,preco,id_escalao_iva) VALUES (5,'Banana',5,1);

    INSERT INTO Colheita (id_produto,data,quantidade,id_setor) VALUES (1,CURRENT_DATE,10,1);
    INSERT INTO Colheita (id_produto,data,quantidade,id_setor) VALUES (2,CURRENT_DATE,10,2);
    INSERT INTO Colheita (id_produto,data,quantidade,id_setor) VALUES (3,CURRENT_DATE,10,1);
    INSERT INTO Colheita (id_produto,data,quantidade,id_setor) VALUES (4,CURRENT_DATE,10,3);
    INSERT INTO Colheita (id_produto,data,quantidade,id_setor) VALUES (5,CURRENT_DATE,10,2);

    rentabilidade.listarSetoresPorQtdProducao();
END;
