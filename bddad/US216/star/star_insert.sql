-- Star data model Inserts --

-- DELETE CONTENT --
DELETE FROM Venda;
DELETE FROM Producao;
DELETE FROM Cliente;
DELETE FROM Produto;
DELETE FROM Setor;
DELETE FROM Hub;
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

INSERT INTO Hub VALUES (1, 'C1');
INSERT INTO Hub VALUES (2, 'C2');
INSERT INTO Hub VALUES (3, 'E1');
INSERT INTO Hub VALUES (4, 'E2');
INSERT INTO Hub VALUES (5, 'P3');

-- Fact table --
-- id_producao, id_produto, id_setor, id_tempo, id_hub, quantidade
-- Setor 1 = Batatas, id_hub = 1
INSERT INTO Producao VALUES (1, 1, 1, 1, 1, 100);
INSERT INTO Producao VALUES (2, 1, 1, 2, 1, 200);
INSERT INTO Producao VALUES (3, 1, 1, 3, 1, 300);
INSERT INTO Producao VALUES (4, 1, 1, 4, 1, 400);
INSERT INTO Producao VALUES (5, 1, 1, 5, 1, 500);
-- Setor 2 = Cenouras, id_hub = 2
INSERT INTO Producao VALUES (6, 2, 2, 1, 2, 600);
INSERT INTO Producao VALUES (7, 2, 2, 2, 2, 700);
INSERT INTO Producao VALUES (8, 2, 2, 3, 2, 800);
INSERT INTO Producao VALUES (9, 2, 2, 4, 2, 900);
INSERT INTO Producao VALUES (10, 2, 2, 5, 2, 1000);
-- Setor 3 = Alfaces, id_hub = 3
INSERT INTO Producao VALUES (11, 3, 3, 1, 3, 1100);
INSERT INTO Producao VALUES (12, 3, 3, 2, 3, 1200);
INSERT INTO Producao VALUES (13, 3, 3, 3, 3, 1300);
INSERT INTO Producao VALUES (14, 3, 3, 4, 3, 1400);
INSERT INTO Producao VALUES (15, 3, 3, 5, 3, 1500);

-- Setor 4 = Tomates, id_hub = 4
INSERT INTO Producao VALUES (16, 4, 4, 1, 4, 1600);
INSERT INTO Producao VALUES (17, 4, 4, 2, 4, 1700);
INSERT INTO Producao VALUES (18, 4, 4, 3, 4, 1800);
INSERT INTO Producao VALUES (19, 4, 4, 4, 4, 1900);
INSERT INTO Producao VALUES (20, 4, 4, 5, 4, 2000);

-- Setor 5 = Maçãs, id_hub = 1
INSERT INTO Producao VALUES (21, 5, 5, 1, 1, 2100);
INSERT INTO Producao VALUES (22, 5, 5, 2, 1, 2200);
INSERT INTO Producao VALUES (23, 5, 5, 3, 1, 2300);
INSERT INTO Producao VALUES (24, 5, 5, 4, 1, 2400);
INSERT INTO Producao VALUES (25, 5, 5, 5, 1, 2500);

-- Setor 6 = Peras, id_hub = 2
INSERT INTO Producao VALUES (26, 6, 6, 1, 2, 2600);
INSERT INTO Producao VALUES (27, 6, 6, 2, 2, 2700);
INSERT INTO Producao VALUES (28, 6, 6, 3, 2, 2800);
INSERT INTO Producao VALUES (29, 6, 6, 4, 2, 2900);
INSERT INTO Producao VALUES (30, 6, 6, 5, 2, 3000);
-- Setor 7 = Laranjas, id_hub = 3
INSERT INTO Producao VALUES (31, 7, 7, 1, 3, 3100);
INSERT INTO Producao VALUES (32, 7, 7, 2, 3, 3200);
INSERT INTO Producao VALUES (33, 7, 7, 3, 3, 3300);
INSERT INTO Producao VALUES (34, 7, 7, 4, 3, 3400);
INSERT INTO Producao VALUES (35, 7, 7, 5, 3, 3500);
-- Setor 8 = Limões, id_hub = 5
INSERT INTO Producao VALUES (36, 8, 8, 1, 5, 3600);
INSERT INTO Producao VALUES (37, 8, 8, 2, 5, 3700);
INSERT INTO Producao VALUES (38, 8, 8, 3, 5, 3800);
INSERT INTO Producao VALUES (39, 8, 8, 4, 5, 3900);
INSERT INTO Producao VALUES (40, 8, 8, 5, 5, 4000);


-- id_venda, id_client, id_produto, id_setor, id_tempo, id_hub, quantidade
-- Setor 1 = Batatas, id_hub = 1
INSERT INTO Venda VALUES (1, 1, 1, 1, 1, 1, 100);
INSERT INTO Venda VALUES (2, 1, 1, 1, 2, 1, 200);
INSERT INTO Venda VALUES (3, 1, 1, 1, 3, 1, 300);
INSERT INTO Venda VALUES (4, 1, 1, 1, 4, 1, 400);
INSERT INTO Venda VALUES (5, 1, 1, 1, 5, 1, 500);

-- Setor 2 = Cenouras, id_hub = 2
INSERT INTO Venda VALUES (6, 2, 2, 2, 1, 2, 600);
INSERT INTO Venda VALUES (7, 2, 2, 2, 2, 2, 700);
INSERT INTO Venda VALUES (8, 2, 2, 2, 3, 2, 800);
INSERT INTO Venda VALUES (9, 2, 2, 2, 4, 2, 900);
INSERT INTO Venda VALUES (10, 2, 2, 2, 5, 2, 1000);

-- Setor 3 = Alfaces, id_hub = 3
INSERT INTO Venda VALUES (11, 3, 3, 3, 1, 3, 1100);
INSERT INTO Venda VALUES (12, 3, 3, 3, 2, 3, 1200);
INSERT INTO Venda VALUES (13, 3, 3, 3, 3, 3, 1300);
INSERT INTO Venda VALUES (14, 3, 3, 3, 4, 3, 1400);
INSERT INTO Venda VALUES (15, 3, 3, 3, 5, 3, 1500);

-- Setor 4 = Tomates, id_hub = 4
INSERT INTO Venda VALUES (16, 1, 4, 4, 1, 4, 1600);
INSERT INTO Venda VALUES (17, 1, 4, 4, 2, 4, 1700);
INSERT INTO Venda VALUES (18, 1, 4, 4, 3, 4, 1800);
INSERT INTO Venda VALUES (19, 1, 4, 4, 4, 4, 1900);
INSERT INTO Venda VALUES (20, 1, 4, 4, 5, 4, 2000);

-- Setor 5 = Maçãs, id_hub = 1
INSERT INTO Venda VALUES (21, 2, 5, 5, 1, 1, 2100);
INSERT INTO Venda VALUES (22, 2, 5, 5, 2, 1, 2200);
INSERT INTO Venda VALUES (23, 2, 5, 5, 3, 1, 2300);
INSERT INTO Venda VALUES (24, 2, 5, 5, 4, 1, 2400);
INSERT INTO Venda VALUES (25, 2, 5, 5, 5, 1, 2500);

-- Setor 6 = Peras, id_hub = 2
INSERT INTO Venda VALUES (26, 3, 6, 6, 1, 2, 2600);
INSERT INTO Venda VALUES (27, 3, 6, 6, 2, 2, 2700);
INSERT INTO Venda VALUES (28, 3, 6, 6, 3, 2, 2800);
INSERT INTO Venda VALUES (29, 3, 6, 6, 4, 2, 2900);
INSERT INTO Venda VALUES (30, 3, 6, 6, 5, 2, 3000);

-- Setor 7 = Laranjas, id_hub = 3
INSERT INTO Venda VALUES (31, 1, 7, 7, 1, 3, 3100);
INSERT INTO Venda VALUES (32, 1, 7, 7, 2, 3, 3200);
INSERT INTO Venda VALUES (33, 1, 7, 7, 3, 3, 3300);
INSERT INTO Venda VALUES (34, 1, 7, 7, 4, 3, 3400);
INSERT INTO Venda VALUES (35, 1, 7, 7, 5, 3, 3500);

-- Setor 8 = Limões, id_hub = 5
INSERT INTO Venda VALUES (36, 2, 8, 8, 1, 5, 3600);
INSERT INTO Venda VALUES (37, 2, 8, 8, 2, 5, 3700);
INSERT INTO Venda VALUES (38, 2, 8, 8, 3, 5, 3800);
INSERT INTO Venda VALUES (39, 2, 8, 8, 4, 5, 3900);
INSERT INTO Venda VALUES (40, 2, 8, 8, 5, 5, 4000);

