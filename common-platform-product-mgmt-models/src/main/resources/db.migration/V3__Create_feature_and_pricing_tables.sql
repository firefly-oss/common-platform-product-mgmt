-- V3__Create_feature_and_pricing_tables.sql

-- Product Feature
CREATE TABLE product_feature (
                                 product_feature_id SERIAL PRIMARY KEY,
                                 product_id INTEGER NOT NULL REFERENCES product(product_id),
                                 feature_name VARCHAR(100) NOT NULL,
                                 feature_description TEXT,
                                 feature_type feature_type_enum NOT NULL,
                                 is_mandatory BOOLEAN NOT NULL DEFAULT FALSE,
                                 date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                 date_updated TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Product Pricing
CREATE TABLE product_pricing (
                                 product_pricing_id SERIAL PRIMARY KEY,
                                 product_id INTEGER NOT NULL REFERENCES product(product_id),
                                 pricing_type pricing_type_enum NOT NULL,
                                 amount_value DECIMAL(19,4) NOT NULL,
                                 amount_unit VARCHAR(50) NOT NULL,
                                 pricing_condition TEXT,
                                 effective_date DATE NOT NULL,
                                 expiry_date DATE,
                                 date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                 date_updated TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Product Documentation
CREATE TABLE product_documentation (
                                       product_documentation_id SERIAL PRIMARY KEY,
                                       product_id INTEGER NOT NULL REFERENCES product(product_id),
                                       doc_type doc_type_enum NOT NULL,
                                       document_manager_ref INTEGER NOT NULL,
                                       date_added DATE NOT NULL,
                                       date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                       date_updated TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create indexes
CREATE INDEX idx_product_feature_product ON product_feature(product_id);
CREATE INDEX idx_product_pricing_product ON product_pricing(product_id);
CREATE INDEX idx_product_documentation_product ON product_documentation(product_id);