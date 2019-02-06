create table role
(
  role_id   int auto_increment
    primary key,
  role_name varchar(255) not null
)
  engine = InnoDB;
create table user
(
  user_id                    int auto_increment
    primary key,
  birth_date                 datetime     null,
  email                      varchar(255) not null,
  first_name                 varchar(255) null,
  last_name                  varchar(255) null,
  password                   varchar(255) not null,
  default_address_address_id bigint       null,
  role_role_id               int          not null,
  constraint UK_ob8kqyqqgmefl0aco34akdtpe
    unique (email),
  constraint FKs2ym81xl98n65ndx09xpwxm66
    foreign key (role_role_id) references role (role_id)
)
  engine = InnoDB;
create table address
(
  address_id   bigint auto_increment
    primary key,
  city         varchar(255) not null,
  country      varchar(255) not null,
  number       varchar(255) null,
  postal_code  varchar(255) not null,
  street       varchar(255) not null,
  user_user_id int          not null,
  constraint FKpxpw7qa7k7scwns6b57iio3xg
    foreign key (user_user_id) references user (user_id)
)
  engine = InnoDB;
  alter table user add
  constraint FKshd60bvbse1d38tmf8jmj87j5
    foreign key (default_address_address_id) references address (address_id);
create table category
(
  category_id int auto_increment
    primary key,
  name        varchar(255) not null
)
  engine = InnoDB;
create table product
(
  product_id           bigint auto_increment
    primary key,
  description          longtext     null,
  end_date             datetime     not null,
  max_sales            bigint       null,
  name                 varchar(255) not null,
  category_category_id int          null,
  manufacturer_user_id int          not null,
  constraint FKkfo6vlkbs8rk2ktwyjt713ask
    foreign key (manufacturer_user_id) references user (user_id),
  constraint FKle1pobdrc8a2uw97gukfmvan4
    foreign key (category_category_id) references category (category_id)
)
  engine = InnoDB;
create table payment_method
(
  payment_method_id bigint auto_increment
    primary key,
  token             varchar(255) not null,
  type              varchar(255) not null,
  user_user_id      int          not null,
  constraint FKb85gyn7flumxdjw8wjvst81pb
    foreign key (user_user_id) references user (user_id)
)
  engine = InnoDB;
create table user_orders
(
  order_id                         bigint auto_increment
    primary key,
  order_date                       datetime not null,
  address_address_id               bigint   not null,
  payment_method_payment_method_id bigint   not null,
  user_user_id                     int      not null,
  constraint FK3a79tx1s3sik0nckxv3ctkede
    foreign key (address_address_id) references address (address_id),
  constraint FKk3l8l51p5csh379l0fa31c3wd
    foreign key (user_user_id) references user (user_id),
  constraint FKlhw744cul4ae4ipnjpuixk17y
    foreign key (payment_method_payment_method_id) references payment_method (payment_method_id)
)
  engine = InnoDB;
create table product_option
(
  option_id             bigint auto_increment
    primary key,
  image                 varchar(255) null,
  manufaturer_reference varchar(255) null,
  option_name           varchar(255) not null,
  product_product_id    bigint       not null,
  constraint FKf135hq0tq3mo9dttbfhcidbac
    foreign key (product_product_id) references product (product_id)
)
  engine = InnoDB;
create table step
(
  step_id            bigint auto_increment
    primary key,
  threshold          int    not null,
  product_product_id bigint not null,
  constraint FKf8vdfl8uoy17po361nq3w0i9r
    foreign key (product_product_id) references product (product_id)
)
  engine = InnoDB;
create table order_item
(
  dispatchment_date datetime     null,
  quantity          int          not null,
  tracking_number   varchar(255) null,
  option_option_id  bigint       not null,
  order_order_id    bigint       not null,
  primary key (option_option_id, order_order_id),
  constraint FK6nkqyjsflv4uktkqpdcsacluv
    foreign key (option_option_id) references product_option (option_id),
  constraint FKtmwacvl0np4l15yc7c63byq7x
    foreign key (order_order_id) references user_orders (order_id)
)
  engine = InnoDB;
create table price
(
  price            decimal(19, 2) not null,
  option_option_id bigint         not null,
  step_step_id     bigint         not null,
  primary key (option_option_id, step_step_id),
  constraint FK8iiig1dmafh7c6kvp7dvb7x35
    foreign key (step_step_id) references step (step_id),
  constraint FKs03qeovq78b5g1qj2549yj159
    foreign key (option_option_id) references product_option (option_id)
)
  engine = InnoDB;

INSERT INTO role (role_id, role_name) VALUES (1, 'ADMIN');
INSERT INTO role (role_id, role_name) VALUES (2, 'USER');
INSERT INTO role (role_id, role_name) VALUES (3, 'SUPPLIER');


INSERT INTO user (user_id, birth_date, email, first_name, last_name, password, default_address_address_id, role_role_id) VALUES (1, '1996-06-03 00:00:00', 'theo.basty@telecom-st-etienne.fr', 'Théo', 'Basty', '$2a$12$tSYPw1WzkuG/hmgMwtue/.duktRg.FQ9yVKGylUsup/AU8VHcnR3q', null, 1);
INSERT INTO user (user_id, birth_date, email, first_name, last_name, password, default_address_address_id, role_role_id) VALUES (2, null, 'cyril.faisandier@telecom-st-etienne.fr', 'Cyril', 'Faisandier', '0000', null, 1);
INSERT INTO user (user_id, birth_date, email, first_name, last_name, password, default_address_address_id, role_role_id) VALUES (3, null, 'baptiste.wolf@telcom-st-etienne.fr', 'Baptiste', 'Wolf', '0000', null, 2);
INSERT INTO user (user_id, birth_date, email, first_name, last_name, password, default_address_address_id, role_role_id) VALUES (4, null, 'raphael.chevasson@telecom-st-etienne.fr', 'Raphaël', 'Chevasson', '0000', null, 2);
INSERT INTO user (user_id, birth_date, email, first_name, last_name, password, default_address_address_id, role_role_id) VALUES (5, null, 'julien.subercaze@telecom-st-etienne.fr', 'Julien', 'Subercaze', '0000', null, 3);
INSERT INTO user (user_id, birth_date, email, first_name, last_name, password, default_address_address_id, role_role_id) VALUES (6, null, 'jacques.fayolle@telecom-st-etienne.fr', 'Jacques', 'Fayolle', '0000', null, 3);


INSERT INTO address (address_id, city, country, number, postal_code, street, user_user_id) VALUES (1, 'Saint Étienne', 'France', '25', '42000', 'Rue du docteur Rémy Annino', 1);
INSERT INTO address (address_id, city, country, number, postal_code, street, user_user_id) VALUES (2, 'Mulhouse', 'France', '45', '68100', 'Rue de la victoire', 2);
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


INSERT INTO product_option (option_id, image, manufaturer_reference, option_name, product_product_id) VALUES (1, null, 'L100', 'Small Storage(16Go)', 1);
INSERT INTO product_option (option_id, image, manufaturer_reference, option_name, product_product_id) VALUES (2, null, 'X100', 'Large Storage(64Go)', 1);
INSERT INTO product_option (option_id, image, manufaturer_reference, option_name, product_product_id) VALUES (3, null, 'AluminiumShov', 'Aluminium', 2);
INSERT INTO product_option (option_id, image, manufaturer_reference, option_name, product_product_id) VALUES (4, null, 'StainlessShov', 'Stainless Steel', 2);
INSERT INTO product_option (option_id, image, manufaturer_reference, option_name, product_product_id) VALUES (5, null, 'BlueLong', 'Blue', 3);
INSERT INTO product_option (option_id, image, manufaturer_reference, option_name, product_product_id) VALUES (6, null, 'RedLong', 'Red', 3);
INSERT INTO product_option (option_id, image, manufaturer_reference, option_name, product_product_id) VALUES (7, null, 'MenString', 'Men', 4);
INSERT INTO product_option (option_id, image, manufaturer_reference, option_name, product_product_id) VALUES (8, null, 'WomenString', 'Women', 4);
INSERT INTO product_option (option_id, image, manufaturer_reference, option_name, product_product_id) VALUES (9, null, 'CherryBlueKB', 'Mechanical', 5);
INSERT INTO product_option (option_id, image, manufaturer_reference, option_name, product_product_id) VALUES (10, null, 'MembraneKB', 'Office', 5);
INSERT INTO product_option (option_id, image, manufaturer_reference, option_name, product_product_id) VALUES (11, null, 'NASAShield', 'Rocket', 6);
INSERT INTO product_option (option_id, image, manufaturer_reference, option_name, product_product_id) VALUES (12, null, 'CarSield', 'Car', 6);


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