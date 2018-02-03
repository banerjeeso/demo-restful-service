DROP TABLE student IF EXISTS;

CREATE TABLE student (
  id   INTEGER PRIMARY KEY,
  name VARCHAR(30),
  gender  VARCHAR(10),
  age INTEGER
);


INSERT INTO student (id,name,gender,age) 
VALUES (1,'Somnath', 'Male',40);
INSERT INTO student (id,name,gender,age) 
VALUES (2, 'Chaitali', 'Female', 35);
INSERT INTO student (id,name,gender,age) 
VALUES (3,'Alina', 'Female',1);
INSERT INTO student (id,name,gender,age) 
VALUES (4,'Anushikha', 'Female',7);
