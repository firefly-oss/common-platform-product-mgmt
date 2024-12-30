-- V2__Create_base_tables.sql

-- Product Category
CREATE TABLE product_category (
                                  product_category_id SERIAL PRIMARY KEY,
                                  category_name VARCHAR(100) NOT NULL,
                                  category_description TEXT,
                                  parent_category_id INTEGER REFERENCES product_category(product_category_id),
                                  date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                  date_updated TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Product Subtype
CREATE TABLE product_subtype (
                                 product_subtype_id SERIAL PRIMARY KEY,
                                 product_category_id INTEGER NOT NULL REFERENCES product_category(product_category_id),
                                 subtype_name VARCHAR(100) NOT NULL,
                                 subtype_description TEXT,
                                 date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                 date_updated TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Product
CREATE TABLE product (
                         product_id SERIAL PRIMARY KEY,
                         product_subtype_id INTEGER NOT NULL REFERENCES product_subtype(product_subtype_id),
                         product_type product_type_enum NOT NULL,
                         product_name VARCHAR(200) NOT NULL,
                         product_code VARCHAR(50) NOT NULL,
                         product_description TEXT,
                         product_status product_status_enum NOT NULL,
                         launch_date DATE,
                         end_date DATE,
                         date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         date_updated TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create indexes
CREATE INDEX idx_product_category_parent ON product_category(parent_category_id);
CREATE INDEX idx_product_subtype_category ON product_subtype(product_category_id);
CREATE INDEX idx_product_subtype ON product(product_subtype_id);