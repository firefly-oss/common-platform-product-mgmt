-- V6__Create_version_and_localization_tables.sql

-- Product Version
CREATE TABLE IF NOT EXISTS product_version (
                                 product_version_id SERIAL PRIMARY KEY,
                                 product_id INTEGER NOT NULL REFERENCES product(product_id),
                                 version_number INTEGER NOT NULL,
                                 version_description TEXT,
                                 effective_date DATE NOT NULL,
                                 date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                 date_updated TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Product Localization
CREATE TABLE IF NOT EXISTS product_localization (
                                      product_localization_id SERIAL PRIMARY KEY,
                                      product_id INTEGER NOT NULL REFERENCES product(product_id),
                                      language_code VARCHAR(5) NOT NULL,
                                      localized_name VARCHAR(200) NOT NULL,
                                      localized_description TEXT,
                                      date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                      date_updated TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Product Pricing Localization
CREATE TABLE IF NOT EXISTS product_pricing_localization (
                                              product_pricing_localization_id SERIAL PRIMARY KEY,
                                              product_pricing_id INTEGER NOT NULL REFERENCES product_pricing(product_pricing_id),
                                              currency_code VARCHAR(3) NOT NULL,
                                              localized_amount_value DECIMAL(19,4) NOT NULL,
                                              date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                              date_updated TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create indexes
CREATE INDEX idx_product_version_product ON product_version(product_id);
CREATE INDEX idx_product_localization_product ON product_localization(product_id);
CREATE INDEX idx_product_pricing_localization_pricing ON product_pricing_localization(product_pricing_id);