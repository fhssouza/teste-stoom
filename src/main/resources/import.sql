
-- Inserir Categorias
INSERT INTO category (id, name, create_at, update_at, active) VALUES (nextval('CATEGORY_SEQ'), 'Eletrônicos', '2022-01-01', '2023-12-31', true);
INSERT INTO category (id, name, create_at, update_at, active) VALUES (nextval('CATEGORY_SEQ'), 'Vestuário', '2022-01-01', '2023-12-31', true);
INSERT INTO category (id, name, create_at, update_at, active) VALUES (nextval('CATEGORY_SEQ'), 'Brinquedos', '2022-01-01', '2023-12-31', true);
INSERT INTO category (id, name, create_at, update_at, active) VALUES (nextval('CATEGORY_SEQ'), 'Livros', '2022-01-01', '2023-12-31', true);
INSERT INTO category (id, name, create_at, update_at, active) VALUES (nextval('CATEGORY_SEQ'), 'Móveis', '2022-01-01', '2023-12-31', true);

-- Inserir Marcas
INSERT INTO brand (id, name, create_at, update_at, active) VALUES (nextval('BRAND_SEQ'), 'Samsung', '2020-01-01', '2030-12-31', true);
INSERT INTO brand (id, name, create_at, update_at, active) VALUES (nextval('BRAND_SEQ'), 'Nike', '2015-01-01', '2030-12-31', true);
INSERT INTO brand (id, name, create_at, update_at, active) VALUES (nextval('BRAND_SEQ'), 'Lego', '2022-01-01', '2030-12-31', true);
INSERT INTO brand (id, name, create_at, update_at, active) VALUES (nextval('BRAND_SEQ'), 'Apple', '2020-01-01', '2030-12-31', true);
INSERT INTO brand (id, name, create_at, update_at, active) VALUES (nextval('BRAND_SEQ'), 'Ikea', '2018-01-01', '2030-12-31', true);

-- Inserir Produtos usando a sequência PRODUCT_SEQ
INSERT INTO product (id, sku, name, description, brand_id, stock, created_at, updated_at, image_url, active) VALUES (nextval('PRODUCT_SEQ'), 'ELEC123', 'Smartphone Samsung Galaxy S21', 'Smartphone com tela de 6.5 polegadas', 1, 100, '2023-01-01 10:00:00', '2023-01-01 10:00:00', 'https://example.com/images/galaxy_s21.jpg', true);
INSERT INTO product (id, sku, name, description, brand_id, stock, created_at, updated_at, image_url, active) VALUES (nextval('PRODUCT_SEQ'), 'NIKS456', 'Tênis Nike Air Max', 'Tênis esportivo confortável e estiloso', 2, 200, '2023-02-01 10:00:00', '2023-02-01 10:00:00', 'https://example.com/images/nike_air_max.jpg', true);
INSERT INTO product (id, sku, name, description, brand_id, stock, created_at, updated_at, image_url, active) VALUES (nextval('PRODUCT_SEQ'), 'LEG789', 'Lego Star Wars Millennium Falcon', 'Brinquedo Lego para montagem do Millennium Falcon', 3, 50, '2023-03-01 10:00:00', '2023-03-01 10:00:00', 'https://example.com/images/lego_millennium_falcon.jpg', true);
INSERT INTO product (id, sku, name, description, brand_id, stock, created_at, updated_at, image_url, active) VALUES (nextval('PRODUCT_SEQ'), 'APP123', 'Apple iPhone 13', 'Smartphone com tela de 6.1 polegadas', 4, 150, '2023-04-01 10:00:00', '2023-04-01 10:00:00', 'https://example.com/images/iphone_13.jpg', true);
INSERT INTO product (id, sku, name, description, brand_id, stock, created_at, updated_at, image_url, active) VALUES (nextval('PRODUCT_SEQ'), 'IKE456', 'Mesa de Jantar Ikea', 'Mesa de jantar de madeira para 6 pessoas', 5, 75, '2023-05-01 10:00:00', '2023-05-01 10:00:00', 'https://example.com/images/ikea_dining_table.jpg', true);

-- Associar Produtos a Categorias
INSERT INTO product_category (product_id, category_id) VALUES (1, 1); -- Smartphone na categoria Eletrônicos
INSERT INTO product_category (product_id, category_id) VALUES (2, 2); -- Tênis Nike na categoria Vestuário
INSERT INTO product_category (product_id, category_id) VALUES (3, 3); -- Lego na categoria Brinquedos
INSERT INTO product_category (product_id, category_id) VALUES (4, 1); -- iPhone na categoria Eletrônicos
INSERT INTO product_category (product_id, category_id) VALUES (5, 5); -- Mesa de Jantar na categoria Móveis

-- Inserir Preços
INSERT INTO price (id, product_id, value, price_type, start_date, end_date) VALUES (nextval('PRICE_SEQ'), 1, 2999.99, 'PROMOCAO', '2023-01-01', '2023-06-30'); -- Promoção do Galaxy S21
INSERT INTO price (id, product_id, value, price_type, start_date, end_date) VALUES (nextval('PRICE_SEQ'), 2, 599.99, 'TAMANHO', '2023-02-01', '2023-12-31'); -- Preço por Tamanho para o tênis Nike
INSERT INTO price (id, product_id, value, price_type, start_date, end_date) VALUES (nextval('PRICE_SEQ'), 3, 799.99, 'REGIAO', '2023-03-01', '2023-12-31'); -- Preço regional para o Lego
INSERT INTO price (id, product_id, value, price_type, start_date, end_date) VALUES (nextval('PRICE_SEQ'), 4, 3999.99, 'PROMOCAO', '2023-04-01', '2023-12-31'); -- Promoção do iPhone 13
INSERT INTO price (id, product_id, value, price_type, start_date, end_date) VALUES (nextval('PRICE_SEQ'), 5, 999.99, 'PROMOCAO', '2023-05-01', '2023-12-31'); -- Promoção da Mesa de Jantar