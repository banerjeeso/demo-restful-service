CREATE TABLE product (
  id   INTEGER PRIMARY KEY,
  productname VARCHAR(30),
  productype  VARCHAR(10),
  quantity INTEGER,
  promotion INTEGER
);

INSERT INTO product (id,productname,productype,quantity,promotion) 
VALUES (1,'Sony', 'music',40,5);
INSERT INTO product (id,productname,productype,quantity,promotion) 
VALUES (2,'Bose', 'music',41,5);
INSERT INTO product (id,productname,productype,quantity,promotion) 
VALUES (3,'Harman', 'music',42,5);
INSERT INTO product (id,productname,productype,quantity,promotion) 
VALUES (4,'JBL', 'music',42,5);
