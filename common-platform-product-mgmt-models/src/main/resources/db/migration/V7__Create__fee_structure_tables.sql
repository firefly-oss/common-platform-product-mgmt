-- V7__Create_fee_structure_tables.sql

-- Fee Structure
CREATE TABLE IF NOT EXISTS fee_structure (
                               fee_structure_id SERIAL PRIMARY KEY,
                               fee_structure_name VARCHAR(100) NOT NULL,
                               fee_structure_description TEXT,
                               fee_structure_type fee_structure_type_enum NOT NULL,
                               date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                               date_updated TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Fee Component
CREATE TABLE IF NOT EXISTS fee_component (
                               fee_component_id SERIAL PRIMARY KEY,
                               fee_structure_id INTEGER NOT NULL REFERENCES fee_structure(fee_structure_id),
                               fee_type fee_type_enum NOT NULL,
                               fee_description TEXT,
                               fee_amount DECIMAL(19,4) NOT NULL,
                               fee_unit fee_unit_enum NOT NULL,
                               applicable_conditions TEXT,
                               date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                               date_updated TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Product Fee Structure
CREATE TABLE IF NOT EXISTS product_fee_structure (
                                       product_fee_structure_id SERIAL PRIMARY KEY,
                                       product_id INTEGER NOT NULL REFERENCES product(product_id),
                                       fee_structure_id INTEGER NOT NULL REFERENCES fee_structure(fee_structure_id),
                                       priority INTEGER NOT NULL,
                                       date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                       date_updated TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Fee Application Rule
CREATE TABLE IF NOT EXISTS fee_application_rule (
                                      fee_application_rule_id SERIAL PRIMARY KEY,
                                      fee_component_id INTEGER NOT NULL REFERENCES fee_component(fee_component_id),
                                      rule_description TEXT,
                                      rule_conditions JSONB NOT NULL,
                                      effective_date DATE NOT NULL,
                                      expiry_date DATE,
                                      date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                      date_updated TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create indexes
CREATE INDEX idx_fee_component_structure ON fee_component(fee_structure_id);
CREATE INDEX idx_product_fee_structure_product ON product_fee_structure(product_id);
CREATE INDEX idx_product_fee_structure_fee ON product_fee_structure(fee_structure_id);
CREATE INDEX idx_fee_application_rule_component ON fee_application_rule(fee_component_id);