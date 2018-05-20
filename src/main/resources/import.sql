INSERT INTO administrator (id, email, name, password) VALUES (1, 'default@gmail.com', 'Default', '37a8eec1ce19687d132fe29051dca629d164e2c4958ba141d5f4133a33f0688f');

INSERT INTO customer(id, email, name, password) VALUES (1, 'customer@gmail.com', 'Customer', '37a8eec1ce19687d132fe29051dca629d164e2c4958ba141d5f4133a33f0688f');
INSERT INTO customer(id, email, name, password) VALUES (2, 'customerDel@gmail.com', 'Deleter', '37a8eec1ce19687d132fe29051dca629d164e2c4958ba141d5f4133a33f0688f');

INSERT INTO product (id, title, author, synopsis, format, price, stock) VALUES (1, 'Guerra y Paz','Reverte', 'Descripcion de guerra y paz','dura',25, 2);
INSERT INTO product (id, title, author, synopsis, format, price, stock) VALUES (2, 'Orgullo y Prejuicio','Rerrojo','Descripcion de orgullo y prejuicio','muy dura', 20, 8);
INSERT INTO product (id, title, author, synopsis, format, price, stock) VALUES (3, 'Zipi y Zape','Reazul','Descripcion de zipi y zape', 'duracell',14, 2);

INSERT INTO image (id, url, product_id) VALUES (1, 'C:\Users\Dani\Desktop\porno\23dc5530de36a2159b1f9bb59d17efcc180a17b5b2cd115ebc1ac9365e9114fc.png', 3)

INSERT INTO sell (id, state, date, customer_id) VALUES (1, 1, NOW(), 1);
INSERT INTO sell (id, state, date, customer_id) VALUES (2, 2, NOW(), 2);

INSERT INTO product_sell (id, cuantity, product_id, sell_id) VALUES (1, 10, 3, 1);
INSERT INTO product_sell (id, cuantity, product_id, sell_id) VALUES (2, 5, 1, 1);
INSERT INTO product_sell (id, cuantity, product_id, sell_id) VALUES (3, 2, 2, 1);

INSERT INTO product_sell (id, cuantity, product_id, sell_id) VALUES (4, 300, 1, 2);
INSERT INTO product_sell (id, cuantity, product_id, sell_id) VALUES (5, 5050, 2, 2);
