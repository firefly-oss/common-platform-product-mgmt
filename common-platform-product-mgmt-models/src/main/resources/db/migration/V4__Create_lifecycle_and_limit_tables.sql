-- V4__Create_lifecycle_and_limit_tables.sql

-- Product Lifecycle
CREATE TABLE IF NOT EXISTS product_lifecycle (
                                   product_lifecycle_id SERIAL PRIMARY KEY,
                                   product_id INTEGER NOT NULL REFERENCES product(product_id),
                                   lifecycle_status lifecycle_status_enum NOT NULL,
                                   status_start_date TIMESTAMP NOT NULL,
                                   status_end_date TIMESTAMP,
                                   reason TEXT,
                                   date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                   date_updated TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Product Limit
CREATE TABLE IF NOT EXISTS product_limit (
                               product_limit_id SERIAL PRIMARY KEY,
                               product_id INTEGER NOT NULL REFERENCES product(product_id),
                               limit_type limit_type_enum NOT NULL,
                               limit_value DECIMAL(19,4) NOT NULL,
                               limit_unit VARCHAR(50) NOT NULL,
                               time_period time_period_enum NOT NULL,
                               effective_date DATE NOT NULL,
                               expiry_date DATE,
                               date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                               date_updated TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create indexes
CREATE INDEX idx_product_lifecycle_product ON product_lifecycle(product_id);
CREATE INDEX idx_product_limit_product ON product_limit(product_id);