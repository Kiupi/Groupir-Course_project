INSERT INTO role (role_id, role_name) VALUES (1, 'ADMIN');
INSERT INTO role (role_id, role_name) VALUES (2, 'USER');
INSERT INTO role (role_id, role_name) VALUES (3, 'SUPPLIER');


INSERT INTO user (user_id, birth_date, email, first_name, last_name, password, default_address_address_id, role_role_id,enabled) VALUES (1, '1996-06-03 00:00:00', 'theo.basty@telecom-st-etienne.fr', 'Théo', 'Basty', '$2a$12$tSYPw1WzkuG/hmgMwtue/.duktRg.FQ9yVKGylUsup/AU8VHcnR3q', null, 1,true);
INSERT INTO user (user_id, birth_date, email, first_name, last_name, password, default_address_address_id, role_role_id,enabled) VALUES (2, null, 'cyril.faisandier@telecom-st-etienne.fr', 'Cyril', 'Faisandier', '$2a$12$AjFcPlQQWrVNZmtVmVxaue31MgGUV6HK5mbLY7PLO6a/41C85oWWy', null, 1,true);
INSERT INTO user (user_id, birth_date, email, first_name, last_name, password, default_address_address_id, role_role_id,enabled) VALUES (3, null, 'baptiste.wolf@telcom-st-etienne.fr', 'Baptiste', 'Wolf', '$2a$12$WqSH0xc6lQt3YMHMe93.EOQ9kSdXv9BG9GMoyoSRGaj7LWO4IRVQe', null, 2,true);
INSERT INTO user (user_id, birth_date, email, first_name, last_name, password, default_address_address_id, role_role_id,enabled) VALUES (4, null, 'raphael.chevasson@telecom-st-etienne.fr', 'Raphaël', 'Chevasson', '$2a$12$c5IQwOw6ozbJbW5CdpVOjOKxd/ApEPNzGW425NFfAw35h2sLJ3kcG', null, 2,true);
INSERT INTO user (user_id, birth_date, email, first_name, last_name, password, default_address_address_id, role_role_id,enabled) VALUES (5, null, 'julien.subercaze@telecom-st-etienne.fr', 'Julien', 'Subercaze', '$2a$12$myAby57ot5nLdV6CFyjXhe1N8WSDWdScQ69o2Q4TAEv8JWeXjclvO', null, 3,true);
INSERT INTO user (user_id, birth_date, email, first_name, last_name, password, default_address_address_id, role_role_id,enabled) VALUES (6, null, 'jacques.fayolle@telecom-st-etienne.fr', 'Jacques', 'Fayolle', '$2a$12$TGo1tT47VfjrvTzWELuI7.NzLn7f5CtYco5VUrlirqCTnvE4CQwCO', null, 3,true);


INSERT INTO address (address_id, city, country, number, postal_code, street, user_user_id) VALUES (1, 'Saint Étienne', 'France', '25', '42000', 'Rue du docteur Rémy Annino', 1);
INSERT INTO address (address_id, city, country, number, postal_code, street, user_user_id) VALUES (2, 'Mulhouse', 'France', '45', '68100', 'Rue de la victoire', 1);
INSERT INTO address (address_id, city, country, number, postal_code, street, user_user_id) VALUES (3, 'Chartres', 'France', '72', '28000', 'Place de la libération', 3);
INSERT INTO address (address_id, city, country, number, postal_code, street, user_user_id) VALUES (4, 'Paris', 'France', '65', '75013', 'Av. de la soeur Rosalie', 4);
INSERT INTO address (address_id, city, country, number, postal_code, street, user_user_id) VALUES (5, 'Maubeuge', 'France', '2', '59600', 'Bd. de l''europe', 5);
INSERT INTO address (address_id, city, country, number, postal_code, street, user_user_id) VALUES (6, 'Lyon ', 'France', '42', '69100', 'Impasse Brachet', 6);


INSERT INTO category (category_id, name) VALUES (1, 'Electronic');
INSERT INTO category (category_id, name) VALUES (2, 'Automobile');
INSERT INTO category (category_id, name) VALUES (3, 'Gaming');
INSERT INTO category (category_id, name) VALUES (4, 'Wear');
INSERT INTO category (category_id, name) VALUES (5, 'Gardening');


INSERT INTO product (product_id, description, end_date, max_sales, name, category_category_id, manufacturer_user_id) VALUES (1, 'blah', '2019-02-25 15:00:00', 200, 'Smartphone', 1, 5);
INSERT INTO product (product_id, description, end_date, max_sales, name, category_category_id, manufacturer_user_id) VALUES (2, 'blahh', '2019-01-26 09:08:45', 600, 'Shovel', 5, 6);
INSERT INTO product (product_id, description, end_date, max_sales, name, category_category_id, manufacturer_user_id) VALUES (3, 'bipboup', '2019-02-06 12:00:00', 2000, 'Dress', 4, 6);
INSERT INTO product (product_id, description, end_date, max_sales, name, category_category_id, manufacturer_user_id) VALUES (4, 'hot', '2019-01-17 10:00:25', 42, 'String', 4, 6);
INSERT INTO product (product_id, description, end_date, max_sales, name, category_category_id, manufacturer_user_id) VALUES (5, 'panpan', '2019-03-01 18:00:00', 150, 'Keyboard', 3, 5);
INSERT INTO product (product_id, description, end_date, max_sales, name, category_category_id, manufacturer_user_id) VALUES (6, 'vroum', '2019-01-19 13:13:13', 666, 'Sun Shield', 2, 5);


INSERT INTO payment_method (payment_method_id, token, type, user_user_id) VALUES (1, 'a', 'cb', 3);
INSERT INTO payment_method (payment_method_id, token, type, user_user_id) VALUES (2, 'b', 'cb', 4);


INSERT INTO user_orders (order_id, order_date, address_address_id, payment_method_payment_method_id, user_user_id) VALUES (1, '2019-01-28 09:26:25', 3, 1, 3);
INSERT INTO user_orders (order_id, order_date, address_address_id, payment_method_payment_method_id, user_user_id) VALUES (2, '2019-01-03 09:28:40', 4, 2, 4);


INSERT INTO product_option (option_id, image, manufaturer_reference, option_name, product_product_id) VALUES (1, 'https://cdn.techadvisor.co.uk/cmsdata/features/3513191/free-up-space-android-1600_thumb800.jpg', 'L100', 'Small Storage(16Go)', 1);
INSERT INTO product_option (option_id, image, manufaturer_reference, option_name, product_product_id) VALUES (2, 'https://static.fnac-static.com/multimedia/Images/FR/NR/9b/a2/61/6398619/1540-1/tsp20141031140216/Smartphone-Motorola-Nexus-6-32-Go-Bleu-Nuit.jpg', 'X100', 'Large Storage(64Go)', 1);
INSERT INTO product_option (option_id, image, manufaturer_reference, option_name, product_product_id) VALUES (3, 'http://www.bulldogtools.co.uk/gen/thumbs/3rqb7b5xynb4zbytqenzl3jphe-z6g4y33cyjfkv6nlpsnw5xng7gxxba4g-large.jpg', 'AluminiumShov', 'Aluminium', 2);
INSERT INTO product_option (option_id, image, manufaturer_reference, option_name, product_product_id) VALUES (4, 'https://i.ebayimg.com/images/g/MS0AAOSwrBJa4kPY/s-l500.jpg', 'StainlessShov', 'Stainless Steel', 2);
INSERT INTO product_option (option_id, image, manufaturer_reference, option_name, product_product_id) VALUES (5, 'https://upload.wikimedia.org/wikipedia/en/thumb/a/a8/The_Dress_%28viral_phenomenon%29.png/220px-The_Dress_%28viral_phenomenon%29.png', 'BlueLong', 'Blue', 3);
INSERT INTO product_option (option_id, image, manufaturer_reference, option_name, product_product_id) VALUES (6, 'https://upload.wikimedia.org/wikipedia/en/thumb/a/a8/The_Dress_%28viral_phenomenon%29.png/220px-The_Dress_%28viral_phenomenon%29.png', 'RedLong', 'Gold', 3);
INSERT INTO product_option (option_id, image, manufaturer_reference, option_name, product_product_id) VALUES (7, 'https://ae01.alicdn.com/kf/HTB1nn0NSVXXXXX0aXXXq6xXFXXXC/red-rose-flower-g-string-femme-tangas-women-sexy-thong-lace-panties-woman-panty-womens-underwear.jpg', 'MenString', 'Men', 4);
INSERT INTO product_option (option_id, image, manufaturer_reference, option_name, product_product_id) VALUES (8, 'https://ae01.alicdn.com/kf/HTB1nn0NSVXXXXX0aXXXq6xXFXXXC/red-rose-flower-g-string-femme-tangas-women-sexy-thong-lace-panties-woman-panty-womens-underwear.jpg', 'WomenString', 'Women', 4);
INSERT INTO product_option (option_id, image, manufaturer_reference, option_name, product_product_id) VALUES (9, '', 'CherryBlueKB', 'Mechanical', 5);
INSERT INTO product_option (option_id, image, manufaturer_reference, option_name, product_product_id) VALUES (10, '', 'MembraneKB', 'Office', 5);
INSERT INTO product_option (option_id, image, manufaturer_reference, option_name, product_product_id) VALUES (11, 'http://www.spaceanswers.com/wp-content/uploads/2013/12/Reentry-online.jpg', 'NASAShield', 'Rocket', 6);
INSERT INTO product_option (option_id, image, manufaturer_reference, option_name, product_product_id) VALUES (12, 'https://i.ebayimg.com/images/g/MrwAAOSwvTBZqArW/s-l640.jpg', 'CarSield', 'Car', 6);


INSERT INTO step (step_id, threshold, product_product_id) VALUES (1, 0, 1);
INSERT INTO step (step_id, threshold, product_product_id) VALUES (2, 100, 1);
INSERT INTO step (step_id, threshold, product_product_id) VALUES (3, 0, 2);
INSERT INTO step (step_id, threshold, product_product_id) VALUES (4, 300, 2);
INSERT INTO step (step_id, threshold, product_product_id) VALUES (5, 0, 3);
INSERT INTO step (step_id, threshold, product_product_id) VALUES (6, 500, 3);
INSERT INTO step (step_id, threshold, product_product_id) VALUES (7, 0, 4);
INSERT INTO step (step_id, threshold, product_product_id) VALUES (8, 21, 4);
INSERT INTO step (step_id, threshold, product_product_id) VALUES (9, 0, 5);
INSERT INTO step (step_id, threshold, product_product_id) VALUES (10, 75, 5);
INSERT INTO step (step_id, threshold, product_product_id) VALUES (11, 0, 6);
INSERT INTO step (step_id, threshold, product_product_id) VALUES (12, 333, 6);


INSERT INTO order_item (dispatchment_date, quantity, tracking_number, option_option_id, order_order_id) VALUES (null, 1, null, 1, 1);
INSERT INTO order_item (dispatchment_date, quantity, tracking_number, option_option_id, order_order_id) VALUES (null, 10, null, 3, 2);
INSERT INTO order_item (dispatchment_date, quantity, tracking_number, option_option_id, order_order_id) VALUES (null, 2, null, 6, 2);
INSERT INTO order_item (dispatchment_date, quantity, tracking_number, option_option_id, order_order_id) VALUES (null, 14, null, 7, 1);
INSERT INTO order_item (dispatchment_date, quantity, tracking_number, option_option_id, order_order_id) VALUES (null, 3, null, 8, 2);
INSERT INTO order_item (dispatchment_date, quantity, tracking_number, option_option_id, order_order_id) VALUES (null, 1, null, 10, 2);
INSERT INTO order_item (dispatchment_date, quantity, tracking_number, option_option_id, order_order_id) VALUES (null, 4, null, 11, 1);


INSERT INTO price (price, option_option_id, step_step_id) VALUES (200.00, 1, 1);
INSERT INTO price (price, option_option_id, step_step_id) VALUES (150.00, 1, 2);
INSERT INTO price (price, option_option_id, step_step_id) VALUES (250.00, 2, 1);
INSERT INTO price (price, option_option_id, step_step_id) VALUES (200.00, 2, 2);
INSERT INTO price (price, option_option_id, step_step_id) VALUES (25.00, 3, 3);
INSERT INTO price (price, option_option_id, step_step_id) VALUES (20.00, 3, 4);
INSERT INTO price (price, option_option_id, step_step_id) VALUES (20.00, 4, 3);
INSERT INTO price (price, option_option_id, step_step_id) VALUES (15.00, 4, 4);
INSERT INTO price (price, option_option_id, step_step_id) VALUES (50.00, 5, 5);
INSERT INTO price (price, option_option_id, step_step_id) VALUES (40.00, 5, 6);
INSERT INTO price (price, option_option_id, step_step_id) VALUES (50.00, 6, 5);
INSERT INTO price (price, option_option_id, step_step_id) VALUES (40.00, 6, 6);
INSERT INTO price (price, option_option_id, step_step_id) VALUES (15.00, 7, 7);
INSERT INTO price (price, option_option_id, step_step_id) VALUES (13.00, 7, 8);
INSERT INTO price (price, option_option_id, step_step_id) VALUES (15.00, 8, 7);
INSERT INTO price (price, option_option_id, step_step_id) VALUES (13.00, 8, 8);
INSERT INTO price (price, option_option_id, step_step_id) VALUES (120.00, 9, 9);
INSERT INTO price (price, option_option_id, step_step_id) VALUES (100.00, 9, 10);
INSERT INTO price (price, option_option_id, step_step_id) VALUES (30.00, 10, 9);
INSERT INTO price (price, option_option_id, step_step_id) VALUES (25.00, 10, 10);
INSERT INTO price (price, option_option_id, step_step_id) VALUES (200000.00, 11, 11);
INSERT INTO price (price, option_option_id, step_step_id) VALUES (150000.00, 11, 12);
INSERT INTO price (price, option_option_id, step_step_id) VALUES (5.00, 12, 11);
INSERT INTO price (price, option_option_id, step_step_id) VALUES (4.00, 12, 12);
