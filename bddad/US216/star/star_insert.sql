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
INSERT INTO Venda VALUES (1, 1, 1, 1, 1, 10);
INSERT INTO Venda VALUES (2, 2, 2, 2, 2, 20);
INSERT INTO Venda VALUES (3, 3, 3, 3, 3, 30);
INSERT INTO Venda VALUES (4, 1, 4, 4, 4, 40);
INSERT INTO Venda VALUES (5, 2, 5, 5, 5, 50);
INSERT INTO Venda VALUES (6, 3, 6, 6, 6, 60);
INSERT INTO Venda VALUES (7, 1, 7, 7, 7, 70);
INSERT INTO Venda VALUES (8, 2, 8, 8, 8, 80);

INSERT INTO Producao VALUES (1, 1, 1, 1, 10);
INSERT INTO Producao VALUES (2, 2, 2, 2, 20);
INSERT INTO Producao VALUES (3, 3, 3, 3, 30);
INSERT INTO Producao VALUES (4, 4, 1, 4, 40);
INSERT INTO Producao VALUES (5, 5, 2, 5, 50);
INSERT INTO Producao VALUES (6, 6, 3, 6, 60);
INSERT INTO Producao VALUES (7, 7, 1, 7, 70);
INSERT INTO Producao VALUES (8, 8, 2, 8, 80);

