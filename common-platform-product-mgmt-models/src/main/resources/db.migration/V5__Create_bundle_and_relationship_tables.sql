-- V5__Create_bundle_and_relationship_tables.sql

-- Product Bundle
CREATE TABLE product_bundle (
                                product_bundle_id SERIAL PRIMARY KEY,
                                bundle_name VARCHAR(100) NOT NULL,
                                bundle_description TEXT,
                                bundle_status bundle_status_enum NOT NULL,
                                date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                date_updated TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Product Bundle Item
CREATE TABLE product_bundle_item (
                                     product_bundle_item_id SERIAL PRIMARY KEY,
                                     product_bundle_id INTEGER NOT NULL REFERENCES product_bundle(product_bundle_id),
                                     product_id INTEGER NOT NULL REFERENCES product(product_id),
                                     special_conditions TEXT,
                                     date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                     date_updated TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Product Relationship
CREATE TABLE product_relationship (
                                      product_relationship_id SERIAL PRIMARY KEY,
                                      product_id INTEGER NOT NULL REFERENCES product(product_id),
                                      related_product_id INTEGER NOT NULL REFERENCES product(product_id),
                                      relationship_type relationship_type_enum NOT NULL,
                                      description TEXT,
                                      date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                      date_updated TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create indexes
CREATE INDEX idx_product_bundle_item_bundle ON product_bundle_item(product_bundle_id);
CREATE INDEX idx_product_bundle_item_product ON product_bundle_item(product_id);
CREATE INDEX idx_product_relationship_product ON product_relationship(product_id);
CREATE INDEX idx_product_relationship_related ON product_relationship(related_product_id);