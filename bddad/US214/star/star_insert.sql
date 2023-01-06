-- Star data model Inserts --

-- DELETE CONTENT --
DELETE FROM Venda;
DELETE FROM Producao;
DELETE FROM Cliente;
DELETE FROM Produto;
DELETE FROM Setor;
DELETE FROM Tempo;

-- Dimension tables --
INSERT INTO Cliente VALUES (1, 'Joao', 123456789);
INSERT INTO Cliente VALUES (2, 'Maria', 987654321);
INSERT INTO Cliente VALUES (3, 'Jose', 123456789);

INSERT INTO Produto VALUES (1, 'Temporário', 'Batata');
INSERT INTO Produto VALUES (2, 'Temporário', 'Cenoura');
INSERT INTO Produto VALUES (3, 'Temporário', 'Alface');
INSERT INTO Produto VALUES (4, 'Temporário', 'Tomate');
INSERT INTO Produto VALUES (5, 'Permanente', 'Maçã');
INSERT INTO Produto VALUES (6, 'Permanente', 'Pera');
INSERT INTO Produto VALUES (7, 'Permanente', 'Laranja');
INSERT INTO Produto VALUES (8, 'Permanente', 'Limão');

INSERT INTO Setor VALUES (1, 'Setor Batatas');
INSERT INTO Setor VALUES (2, 'Setor Cenouras');
INSERT INTO Setor VALUES (3, 'Setor Alfaces');
INSERT INTO Setor VALUES (4, 'Setor Tomates');
INSERT INTO Setor VALUES (5, 'Setor Maçãs');
INSERT INTO Setor VALUES (6, 'Setor Peras');
INSERT INTO Setor VALUES (7, 'Setor Laranjas');
INSERT INTO Setor VALUES (8, 'Setor Limões');

INSERT INTO Tempo VALUES (1, 2015, 1);
INSERT INTO Tempo VALUES (2, 2016, 2);
INSERT INTO Tempo VALUES (3, 2017, 3);
INSERT INTO Tempo VALUES (4, 2018, 4);
INSERT INTO Tempo VALUES (5, 2019, 5);
INSERT INTO Tempo VALUES (6, 2020, 6);
INSERT INTO Tempo VALUES (7, 2021, 7);
INSERT INTO Tempo VALUES (8, 2022, 8);

-- Fact table --
-- id_venda, id_cliente, id_produto, id_setor, id_tempo, quantidade
-- Setor 1 = Batatas
INSERT INTO Venda VALUES (1, 1, 1, 1, 1, 100);
INSERT INTO Venda VALUES (2, 1, 1, 1, 2, 200);
INSERT INTO Venda VALUES (3, 1, 1, 1, 3, 300);
-- Setor 2 = Cenouras
INSERT INTO Venda VALUES (4, 2, 2, 2, 3, 100);
INSERT INTO Venda VALUES (5, 2, 2, 2, 4, 200);
INSERT INTO Venda VALUES (6, 2, 2, 2, 5, 300);
INSERT INTO Venda VALUES (7, 2, 2, 2, 6, 400);
INSERT INTO Venda VALUES (8, 2, 2, 2, 7, 500);
INSERT INTO Venda VALUES (9, 2, 2, 2, 8, 600);
-- Setor 3 = Alfaces
INSERT INTO Venda VALUES (10, 3, 3, 3, 1, 100);
INSERT INTO Venda VALUES (11, 3, 3, 3, 2, 200);
INSERT INTO Venda VALUES (12, 3, 3, 3, 3, 300);
INSERT INTO Venda VALUES (13, 3, 3, 3, 4, 400);
INSERT INTO Venda VALUES (14, 3, 3, 3, 5, 500);
INSERT INTO Venda VALUES (15, 3, 3, 3, 6, 600);
INSERT INTO Venda VALUES (16, 3, 3, 3, 7, 700);
INSERT INTO Venda VALUES (17, 3, 3, 3, 8, 800);
-- Setor 4 = Tomates
INSERT INTO Venda VALUES (18, 1, 4, 4, 1, 100);
INSERT INTO Venda VALUES (19, 1, 4, 4, 2, 200);
INSERT INTO Venda VALUES (20, 1, 4, 4, 3, 300);
INSERT INTO Venda VALUES (21, 1, 4, 4, 4, 400);
INSERT INTO Venda VALUES (22, 1, 4, 4, 5, 500);
INSERT INTO Venda VALUES (23, 1, 4, 4, 6, 600);
INSERT INTO Venda VALUES (24, 1, 4, 4, 7, 700);
INSERT INTO Venda VALUES (25, 1, 4, 4, 8, 800);
-- Setor 5 = Maçãs
INSERT INTO Venda VALUES (26, 2, 5, 5, 1, 100);
INSERT INTO Venda VALUES (27, 2, 5, 5, 2, 200);
INSERT INTO Venda VALUES (28, 2, 5, 5, 3, 300);
INSERT INTO Venda VALUES (29, 2, 5, 5, 4, 400);
INSERT INTO Venda VALUES (30, 2, 5, 5, 5, 500);
INSERT INTO Venda VALUES (31, 2, 5, 5, 6, 600);
INSERT INTO Venda VALUES (32, 2, 5, 5, 7, 700);
INSERT INTO Venda VALUES (33, 2, 5, 5, 8, 800);
-- Setor 6 = Peras
INSERT INTO Venda VALUES (34, 3, 6, 6, 1, 100);
INSERT INTO Venda VALUES (35, 3, 6, 6, 2, 200);
INSERT INTO Venda VALUES (36, 3, 6, 6, 3, 300);
INSERT INTO Venda VALUES (37, 3, 6, 6, 4, 400);
INSERT INTO Venda VALUES (38, 3, 6, 6, 5, 500);
INSERT INTO Venda VALUES (39, 3, 6, 6, 6, 600);
INSERT INTO Venda VALUES (40, 3, 6, 6, 7, 700);
INSERT INTO Venda VALUES (41, 3, 6, 6, 8, 800);
-- Setor 7 = Laranjas
INSERT INTO Venda VALUES (42, 1, 7, 7, 1, 100);
INSERT INTO Venda VALUES (43, 1, 7, 7, 2, 200);
INSERT INTO Venda VALUES (44, 1, 7, 7, 3, 300);
INSERT INTO Venda VALUES (45, 1, 7, 7, 4, 400);
INSERT INTO Venda VALUES (46, 1, 7, 7, 5, 500);
INSERT INTO Venda VALUES (47, 1, 7, 7, 6, 600);
INSERT INTO Venda VALUES (48, 1, 7, 7, 7, 700);
INSERT INTO Venda VALUES (49, 1, 7, 7, 8, 800);
-- Setor 8 = Limões
INSERT INTO Venda VALUES (50, 2, 8, 8, 1, 100);
INSERT INTO Venda VALUES (51, 2, 8, 8, 2, 200);
INSERT INTO Venda VALUES (52, 2, 8, 8, 3, 300);
INSERT INTO Venda VALUES (53, 2, 8, 8, 4, 400);
INSERT INTO Venda VALUES (54, 2, 8, 8, 5, 500);
INSERT INTO Venda VALUES (55, 2, 8, 8, 6, 600);
INSERT INTO Venda VALUES (56, 2, 8, 8, 7, 700);
INSERT INTO Venda VALUES (57, 2, 8, 8, 8, 800);


-- id_producao, id_produto, id_setor, id_tempo, quantidade
-- Setor 1 = Batatas
INSERT INTO Producao VALUES (1, 1, 1, 1, 100);
INSERT INTO Producao VALUES (2, 1, 1, 2, 200);
INSERT INTO Producao VALUES (3, 1, 1, 3, 300);
-- Setor 2 = Cenouras
INSERT INTO Producao VALUES (4, 2, 2, 3, 100);
INSERT INTO Producao VALUES (5, 2, 2, 4, 200);
INSERT INTO Producao VALUES (6, 2, 2, 5, 300);
INSERT INTO Producao VALUES (7, 2, 2, 6, 400);
INSERT INTO Producao VALUES (8, 2, 2, 7, 500);
INSERT INTO Producao VALUES (9, 2, 2, 8, 600);
-- Setor 3 = Alfaces
INSERT INTO Producao VALUES (10, 3, 3, 1, 100);
INSERT INTO Producao VALUES (11, 3, 3, 2, 200);
INSERT INTO Producao VALUES (12, 3, 3, 3, 300);
INSERT INTO Producao VALUES (13, 3, 3, 4, 400);
INSERT INTO Producao VALUES (14, 3, 3, 5, 500);
INSERT INTO Producao VALUES (15, 3, 3, 6, 600);
INSERT INTO Producao VALUES (16, 3, 3, 7, 700);
INSERT INTO Producao VALUES (17, 3, 3, 8, 800);
-- Setor 4 = Tomates
INSERT INTO Producao VALUES (18, 4, 4, 1, 100);
INSERT INTO Producao VALUES (19, 4, 4, 2, 200);
INSERT INTO Producao VALUES (20, 4, 4, 3, 300);
INSERT INTO Producao VALUES (21, 4, 4, 4, 400);
INSERT INTO Producao VALUES (22, 4, 4, 5, 500);
INSERT INTO Producao VALUES (23, 4, 4, 6, 600);
INSERT INTO Producao VALUES (24, 4, 4, 7, 700);
INSERT INTO Producao VALUES (25, 4, 4, 8, 800);
-- Setor 5 = Maçãs
INSERT INTO Producao VALUES (26, 5, 5, 1, 100);
INSERT INTO Producao VALUES (27, 5, 5, 2, 200);
INSERT INTO Producao VALUES (28, 5, 5, 3, 300);
INSERT INTO Producao VALUES (29, 5, 5, 4, 400);
INSERT INTO Producao VALUES (30, 5, 5, 5, 500);
INSERT INTO Producao VALUES (31, 5, 5, 6, 600);
INSERT INTO Producao VALUES (32, 5, 5, 7, 700);
INSERT INTO Producao VALUES (33, 5, 5, 8, 800);
-- Setor 6 = Peras
INSERT INTO Producao VALUES (34, 6, 6, 1, 100);
INSERT INTO Producao VALUES (35, 6, 6, 2, 200);
INSERT INTO Producao VALUES (36, 6, 6, 3, 300);
INSERT INTO Producao VALUES (37, 6, 6, 4, 400);
INSERT INTO Producao VALUES (38, 6, 6, 5, 500);
INSERT INTO Producao VALUES (39, 6, 6, 6, 600);
INSERT INTO Producao VALUES (40, 6, 6, 7, 700);
INSERT INTO Producao VALUES (41, 6, 6, 8, 800);
-- Setor 7 = Laranjas
INSERT INTO Producao VALUES (42, 7, 7, 1, 100);
INSERT INTO Producao VALUES (43, 7, 7, 2, 200);
INSERT INTO Producao VALUES (44, 7, 7, 3, 300);
INSERT INTO Producao VALUES (45, 7, 7, 4, 400);
INSERT INTO Producao VALUES (46, 7, 7, 5, 500);
INSERT INTO Producao VALUES (47, 7, 7, 6, 600);
INSERT INTO Producao VALUES (48, 7, 7, 7, 700);
INSERT INTO Producao VALUES (49, 7, 7, 8, 800);
-- Setor 8 = Limões
INSERT INTO Producao VALUES (50, 8, 8, 1, 100);
INSERT INTO Producao VALUES (51, 8, 8, 2, 200);
INSERT INTO Producao VALUES (52, 8, 8, 3, 300);
INSERT INTO Producao VALUES (53, 8, 8, 4, 400);
INSERT INTO Producao VALUES (54, 8, 8, 5, 500);
INSERT INTO Producao VALUES (55, 8, 8, 6, 600);
INSERT INTO Producao VALUES (56, 8, 8, 7, 700);
INSERT INTO Producao VALUES (57, 8, 8, 8, 800);

