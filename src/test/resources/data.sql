SET NON_KEYWORDS VALUE;

--USERS

INSERT INTO users (avatar, first_name, last_name, username, email, password, birth_date, phone_number, enabled) VALUES 
    ('avatar1.png', 'alejandro', 'retamal', 'alejandro10', 'alejandro@gmail.com', 'alejandro12345', '1996-10-04', '+56952419637', true),
    ('avatar2.png', 'ester', 'guevara', 'ester17', 'ester@gmail.com', 'ester12345', '1994-01-17', '+56932189745', true),
    ('avatar3.png', 'pepe', 'pepon', 'pepe58', 'pepe@gmail.com', 'pepe12345', '1992-12-26', '+56931258759', true);


-- ROLES

INSERT INTO roles (name)  VALUES 
    ('ROLE_ADMIN'),
    ('ROLE_USER'),
    ('ROLE_SELLER'),
    ('ROLE_GUEST');

INSERT INTO users_roles (user_id, role_id) VALUES
    (1, 2),
    (2, 2),
    (3, 2);


-- ADDRESSES

INSERT INTO addresses (user_id, title, address_line_1, address_line_2, country, city, commune, postal_code, landmark) 
    VALUES (1, 'Casa en Antofagasta', 'Avenida Argentina 123', 'Departamento 5A', 'Chile', 'Antofagasta', 'Antofagasta', '1240000', 'Cerca del Mall Plaza Antofagasta');

INSERT INTO addresses (user_id, title, address_line_1, address_line_2, country, city, commune, postal_code, landmark) 
    VALUES (1, 'Oficina en Rancagua', 'Calle Estado 456', 'Piso 3', 'Chile', 'Rancagua', 'Rancagua', '2820000', 'Frente a la Plaza de Los Héroes');

INSERT INTO addresses (user_id, title, address_line_1, address_line_2, country, city, commune, postal_code, landmark) 
    VALUES (2, 'Departamento en Puerto Montt', 'Avenida Diego Portales 789', 'Departamento 10B', 'Chile', 'Puerto Montt', 'Puerto Montt', '5480000', 'Cerca del Costanera Center');

INSERT INTO addresses (user_id, title, address_line_1, address_line_2, country, city, commune, postal_code, landmark) 
    VALUES (2, 'Casa en Iquique', 'Calle Thompson 1011', '', 'Chile', 'Iquique', 'Iquique', '1100000', 'Frente a la playa Cavancha');

INSERT INTO addresses (user_id, title, address_line_1, address_line_2, country, city, commune, postal_code, landmark) 
    VALUES (3, 'Casa en Chillán', 'Avenida O’Higgins 1213', '', 'Chile', 'Chillán', 'Chillán', '3780000', 'Cerca de la Plaza de Armas');

INSERT INTO addresses (user_id, title, address_line_1, address_line_2, country, city, commune, postal_code, landmark) 
    VALUES (3, 'Casa en Punta Arenas', 'Calle Magallanes 1415', '', 'Chile', 'Punta Arenas', 'Punta Arenas', '6200000', 'Frente al Estrecho de Magallanes');


-- CATEGORIES

INSERT INTO categories (name, description, parent)
    VALUES ('Hombre', 'Descripcion hombre', null);

INSERT INTO categories (name, description, parent)
    VALUES ('Tecnologia', 'Descripcion tecnologia', null);
    
INSERT INTO categories (name, description, parent)
    VALUES ('Deportes', 'Equipamiento deportivo, ropa deportiva, accesorios', null);

INSERT INTO categories (name, description, parent)
    VALUES ('Jeans-hombre', 'Descripcion jeans hombre', 1);

INSERT INTO categories (name, description, parent)
    VALUES ('Poleras-hombre', 'Descripcion poleras hombre', 1);

INSERT INTO categories (name, description, parent)
    VALUES ('Smartphone', 'Telefonos celulares', 2);

INSERT INTO categories (name, description, parent)
    VALUES ('Notebooks', 'Computadores portatiles', 2);

INSERT INTO categories (name, description, parent)
    VALUES ('Futbol', 'Descripcion futbol', 3);

INSERT INTO categories (name, description, parent)
    VALUES ('Unknown', 'Unknown', null);


-- BRANDS

INSERT INTO brands (name, description, logo_url)
    VALUES ('Lee', 'Marca líder en moda', 'https://example.com/lee_logo.png');

INSERT INTO brands (name, description, logo_url)
    VALUES ('Apple', 'Marca líder en tecnologia', 'https://example.com/apple_logo.png');

INSERT INTO brands (name, description, logo_url)
    VALUES ('ASUS', 'Empresa multinacional de tecnología', 'https://example.com/asus_logo.png');

INSERT INTO brands (name, description, logo_url)
    VALUES ('Nike', 'Marca deportiva estadounidense', 'https://example.com/nike_logo.png');

INSERT INTO brands (name, description, logo_url)
    VALUES ('Puma', 'Marca deportiva alemana', 'https://example.com/puma_logo.png');

INSERT INTO brands (name, description, logo_url)
    VALUES ('Unknown', 'Unknown', 'https://example.com/unknown.png');


-- PRODUCTS

INSERT INTO products (name, description, category_id, brand_id, cover) 
    VALUES ('iPhone 15', 'Smartphone Apple', 6, 2, 'https://example.com/images/iphone15.jpg');

INSERT INTO products (name, description, category_id, brand_id, cover) 
    VALUES ('Asus Zenbook', 'Notebook de ultima generacion', 7, 3, 'https://example.com/images/asus_zenbook.jpg');

INSERT INTO products (name, description, category_id, brand_id, cover) 
    VALUES ('Balon premier league 2025', 'Balon oficial Premier League', 8, 4, 'https://example.com/images/balon_pl.jpg');

INSERT INTO products (name, description, category_id, brand_id, cover) 
    VALUES ('Jeans Lee', 'Descripcion pantalones Lee', 4, 1, 'https://example.com/images/jeans_lee.jpg');

INSERT INTO products (name, description, category_id, brand_id, cover) 
    VALUES ('Polera Puma', 'Descripcion polera Puma', 5, 5, 'https://example.com/images/polera_puma.jpg');


-- PRODUCTS_ATTRIBUTES

INSERT INTO product_attributes (type, value)
    VALUES ('color', 'red');

INSERT INTO product_attributes (type, value)
    VALUES ('color', 'blue');

INSERT INTO product_attributes (type, value)
    VALUES ('color', 'black');

INSERT INTO product_attributes (type, value)
    VALUES ('size', 'S');

INSERT INTO product_attributes (type, value)
    VALUES ('size', 'M');

INSERT INTO product_attributes (type, value)
    VALUES ('size', 'L');

INSERT INTO product_attributes (type, value)
    VALUES ('color', 'none-color');

INSERT INTO product_attributes (type, value)
    VALUES ('size', 'none-size');


-- PRODUCTS_SKU

INSERT INTO product_skus (product_id, size_attribute_id, color_attribute_id, sku, price, stock, is_active, is_featured, is_on_sale)
    VALUES (1, 8, 3, 'SKU2210', 1500.99, 540, TRUE, TRUE, TRUE);

INSERT INTO product_skus (product_id, size_attribute_id, color_attribute_id, sku, price, stock, is_active, is_featured, is_on_sale)
    VALUES (2, 8, 3, 'SKU2501', 999.99, 200, TRUE, FALSE, TRUE);

INSERT INTO product_skus (product_id, size_attribute_id, color_attribute_id, sku, price, stock, is_active, is_featured, is_on_sale)
    VALUES (3, 8, 2, 'SKU9820', 29.99, 958, TRUE, TRUE, TRUE);

INSERT INTO product_skus (product_id, size_attribute_id, color_attribute_id, sku, price, stock, is_active, is_featured, is_on_sale)
    VALUES (4, 4, 1, 'SKU8562', 29.99, 100, TRUE, FALSE, FALSE);

INSERT INTO product_skus (product_id, size_attribute_id, color_attribute_id, sku, price, stock, is_active, is_featured, is_on_sale)
    VALUES (4, 5, 2, 'SKU8563', 29.99, 100, TRUE, FALSE, FALSE);

INSERT INTO product_skus (product_id, size_attribute_id, color_attribute_id, sku, price, stock, is_active, is_featured, is_on_sale)
    VALUES (4, 6, 3, 'SKU8564', 29.99, 100, TRUE, FALSE, FALSE);

INSERT INTO product_skus (product_id, size_attribute_id, color_attribute_id, sku, price, stock, is_active, is_featured, is_on_sale)
    VALUES (5, 4, 1, 'SKU1254', 19.99, 180, TRUE, FALSE, FALSE);
    
INSERT INTO product_skus (product_id, size_attribute_id, color_attribute_id, sku, price, stock, is_active, is_featured, is_on_sale)
    VALUES (5, 5, 2, 'SKU1255', 19.99, 70, TRUE, FALSE, FALSE);

INSERT INTO product_skus (product_id, size_attribute_id, color_attribute_id, sku, price, stock, is_active, is_featured, is_on_sale)
    VALUES (5, 6, 3, 'SKU1256', 19.99, 70, TRUE, FALSE, FALSE);

INSERT INTO product_skus (product_id, size_attribute_id, color_attribute_id, sku, price, stock, is_active, is_featured, is_on_sale)
    VALUES (5, 6, 1, 'SKU1257', 19.99, 70, TRUE, FALSE, FALSE);

INSERT INTO product_skus (product_id, size_attribute_id, color_attribute_id, sku, price, stock, is_active, is_featured, is_on_sale)
    VALUES (5, 5, 3, 'SKU1258', 19.99, 70, TRUE, FALSE, FALSE);


-- DISCOUNTS

INSERT INTO discounts (discount_type, discount_value, start_date, end_date, is_active)
    VALUES ('fixed_amount', 10.00, '2025-03-01 00:00:00', '2025-03-31 23:59:59', TRUE);

INSERT INTO discounts (discount_type, discount_value, start_date, end_date, is_active)
    VALUES ('free_shipping', 5.00, '2025-03-10 00:00:00', '2025-04-15 23:59:59', TRUE);

INSERT INTO discounts (discount_type, discount_value, start_date, end_date, is_active)
    VALUES ('percentage', 20.00, '2025-03-03 00:00:00', '2025-03-31 23:59:59', TRUE);

INSERT INTO discounts (discount_type, discount_value, start_date, end_date, is_active)
    VALUES ('percentage', 10.00, '2025-02-01 00:00:00', '2025-02-28 23:59:59', TRUE);


-- DISCOUNTS_PRODUCT_SKU

INSERT INTO discount_product_sku (discount_id, product_sku_id)
    VALUES (1, 1);

INSERT INTO discount_product_sku (discount_id, product_sku_id)
    VALUES (4, 1);

INSERT INTO discount_product_sku (discount_id, product_sku_id)
    VALUES (2, 1);

INSERT INTO discount_product_sku (discount_id, product_sku_id)
    VALUES (3, 2);

INSERT INTO discount_product_sku (discount_id, product_sku_id)
    VALUES (4, 3);

INSERT INTO discount_product_sku (discount_id, product_sku_id)
    VALUES (2, 3);

INSERT INTO discount_product_sku (discount_id, product_sku_id)
    VALUES (1, 4);

INSERT INTO discount_product_sku (discount_id, product_sku_id)
    VALUES (2, 4);


-- ORDERS

INSERT INTO orders (user_id, total_amount, order_status, payment_status, shipping_address_id, billing_address_id)
    VALUES (1, 89.99, 'PENDING', 'PENDING', 1, 1);

INSERT INTO orders (user_id, total_amount, order_status, payment_status, shipping_address_id, billing_address_id)
    VALUES (1, 49.99, 'PENDING', 'COMPLETED', 1, 1);

INSERT INTO orders (user_id, total_amount, order_status, payment_status, shipping_address_id, billing_address_id)
    VALUES (2, 249.99, 'SHIPPED', 'COMPLETED', 2, 2);

INSERT INTO orders (user_id, total_amount, order_status, payment_status, shipping_address_id, billing_address_id)
    VALUES (2, 39.99, 'PENDING', 'PENDING', 2, 2);

INSERT INTO orders (user_id, total_amount, order_status, payment_status, shipping_address_id, billing_address_id)
    VALUES (3, 49.99, 'SHIPPED', 'COMPLETED', 3, 3);

INSERT INTO orders (user_id, total_amount, order_status, payment_status, shipping_address_id, billing_address_id)
    VALUES (3, 69.99, 'DELIVERED', 'COMPLETED', 3, 3);


-- ORDER_ITEMS

INSERT INTO order_items (order_id, product_sku_id, quantity, unit_price, discount_id)
    VALUES (1, 1, 1, 1500.99, 1);

INSERT INTO order_items (order_id, product_sku_id, quantity, unit_price, discount_id)
    VALUES (1, 2, 2, 999.99, 2);

INSERT INTO order_items (order_id, product_sku_id, quantity, unit_price, discount_id)
    VALUES (2, 3, 3, 29.99, 1);

INSERT INTO order_items (order_id, product_sku_id, quantity, unit_price, discount_id)
    VALUES (2, 7, 2, 19.99, 3);

INSERT INTO order_items (order_id, product_sku_id, quantity, unit_price, discount_id)
    VALUES (3, 5, 1, 29.99, 1);

INSERT INTO order_items (order_id, product_sku_id, quantity, unit_price, discount_id)
    VALUES (4, 6, 2, 29.99, 3);

INSERT INTO order_items (order_id, product_sku_id, quantity, unit_price, discount_id)
    VALUES (5, 1, 1, 1500.99, 2);

INSERT INTO order_items (order_id, product_sku_id, quantity, unit_price, discount_id)
    VALUES (6, 4, 2, 29.99, 1);


-- PAYMENT_DETAILS

INSERT INTO payment_details (order_id, amount, currency, provider, payment_method, transaction_id, status)
    VALUES(1, 15000, 'CLP', 'FLOW', 'WEBPAY', 'TXN123789', 'COMPLETED');

INSERT INTO payment_details (order_id, amount, currency, provider, payment_method, transaction_id, status)
    VALUES(2, 89990, 'CLP', 'FLOW', 'WEBPAY', 'TXN658541', 'COMPLETED');

INSERT INTO payment_details (order_id, amount, currency, provider, payment_method, transaction_id, status)
    VALUES(3, 20000, 'CLP', 'FLOW', 'WEBPAY', 'TXN534894', 'REFUNDED');

INSERT INTO payment_details (order_id, amount, currency, provider, payment_method, transaction_id, status)
    VALUES(4, 45000, 'CLP', 'FLOW', 'WEBPAY', 'TXN235749', 'PENDING');

INSERT INTO payment_details (order_id, amount, currency, provider, payment_method, transaction_id, status)
    VALUES(5, 119990, 'CLP', 'FLOW', 'WEBPAY', 'TXN865234', 'FAILED');

INSERT INTO payment_details (order_id, amount, currency, provider, payment_method, transaction_id, status)
    VALUES(6, 9990, 'CLP', 'FLOW', 'WEBPAY', 'TXN874132', 'COMPLETED');