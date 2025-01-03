-- V9__Create_Triggers.sql
-- Description: Creates triggers for timestamp management and business rules in the product domain

-- Reusing timestamp functions if they don't exist (in case this runs before V19)
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_proc WHERE proname = 'update_timestamp') THEN
CREATE FUNCTION update_timestamp()
    RETURNS TRIGGER AS $func$
BEGIN
            NEW.date_updated = CURRENT_TIMESTAMP;
RETURN NEW;
END;
        $func$ LANGUAGE plpgsql;
END IF;

    IF NOT EXISTS (SELECT 1 FROM pg_proc WHERE proname = 'set_initial_timestamps') THEN
CREATE FUNCTION set_initial_timestamps()
    RETURNS TRIGGER AS $func$
BEGIN
            NEW.date_created = CURRENT_TIMESTAMP;
            NEW.date_updated = CURRENT_TIMESTAMP;
RETURN NEW;
END;
        $func$ LANGUAGE plpgsql;
END IF;
END
$$;

-- === PRODUCT_CATEGORY TABLE TRIGGERS ===
CREATE TRIGGER product_category_timestamp_insert
    BEFORE INSERT ON product_category
    FOR EACH ROW
    EXECUTE FUNCTION set_initial_timestamps();

CREATE TRIGGER product_category_timestamp_update
    BEFORE UPDATE ON product_category
    FOR EACH ROW
    EXECUTE FUNCTION update_timestamp();

-- Prevent circular references in product category hierarchy
CREATE OR REPLACE FUNCTION prevent_category_circular_reference()
RETURNS TRIGGER AS $$
DECLARE
current_id int;
BEGIN
    IF NEW.parent_category_id IS NULL THEN
        RETURN NEW;
END IF;

    current_id := NEW.parent_category_id;
    WHILE current_id IS NOT NULL LOOP
        IF current_id = NEW.product_category_id THEN
            RAISE EXCEPTION 'Circular reference detected in product category hierarchy';
END IF;
SELECT parent_category_id INTO current_id
FROM product_category
WHERE product_category_id = current_id;
END LOOP;
RETURN NEW;
END;
$$ language 'plpgsql';

CREATE TRIGGER prevent_category_circular_ref
    BEFORE INSERT OR UPDATE ON product_category
                         FOR EACH ROW
                         EXECUTE FUNCTION prevent_category_circular_reference();

-- === PRODUCT_SUBTYPE TABLE TRIGGERS ===
CREATE TRIGGER product_subtype_timestamp_insert
    BEFORE INSERT ON product_subtype
    FOR EACH ROW
    EXECUTE FUNCTION set_initial_timestamps();

CREATE TRIGGER product_subtype_timestamp_update
    BEFORE UPDATE ON product_subtype
    FOR EACH ROW
    EXECUTE FUNCTION update_timestamp();

-- === PRODUCT TABLE TRIGGERS ===
CREATE TRIGGER product_timestamp_insert
    BEFORE INSERT ON product
    FOR EACH ROW
    EXECUTE FUNCTION set_initial_timestamps();

CREATE TRIGGER product_timestamp_update
    BEFORE UPDATE ON product
    FOR EACH ROW
    EXECUTE FUNCTION update_timestamp();

-- Validate product dates
CREATE OR REPLACE FUNCTION validate_product_dates()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.end_date IS NOT NULL AND NEW.end_date <= NEW.launch_date THEN
        RAISE EXCEPTION 'Product end date must be after launch date';
END IF;
RETURN NEW;
END;
$$ language 'plpgsql';

CREATE TRIGGER product_dates_validation
    BEFORE INSERT OR UPDATE ON product
                         FOR EACH ROW
                         EXECUTE FUNCTION validate_product_dates();

-- === PRODUCT_FEATURE TABLE TRIGGERS ===
CREATE TRIGGER product_feature_timestamp_insert
    BEFORE INSERT ON product_feature
    FOR EACH ROW
    EXECUTE FUNCTION set_initial_timestamps();

CREATE TRIGGER product_feature_timestamp_update
    BEFORE UPDATE ON product_feature
    FOR EACH ROW
    EXECUTE FUNCTION update_timestamp();

-- === PRODUCT_PRICING TABLE TRIGGERS ===
CREATE TRIGGER product_pricing_timestamp_insert
    BEFORE INSERT ON product_pricing
    FOR EACH ROW
    EXECUTE FUNCTION set_initial_timestamps();

CREATE TRIGGER product_pricing_timestamp_update
    BEFORE UPDATE ON product_pricing
    FOR EACH ROW
    EXECUTE FUNCTION update_timestamp();

-- Validate pricing dates
CREATE OR REPLACE FUNCTION validate_pricing_dates()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.expiry_date IS NOT NULL AND NEW.expiry_date <= NEW.effective_date THEN
        RAISE EXCEPTION 'Pricing expiry date must be after effective date';
END IF;
RETURN NEW;
END;
$$ language 'plpgsql';

CREATE TRIGGER product_pricing_dates
    BEFORE INSERT OR UPDATE ON product_pricing
                         FOR EACH ROW
                         EXECUTE FUNCTION validate_pricing_dates();

-- === PRODUCT_DOCUMENTATION TABLE TRIGGERS ===
CREATE TRIGGER product_documentation_timestamp_insert
    BEFORE INSERT ON product_documentation
    FOR EACH ROW
    EXECUTE FUNCTION set_initial_timestamps();

CREATE TRIGGER product_documentation_timestamp_update
    BEFORE UPDATE ON product_documentation
    FOR EACH ROW
    EXECUTE FUNCTION update_timestamp();

-- === PRODUCT_LIFECYCLE TABLE TRIGGERS ===
CREATE TRIGGER product_lifecycle_timestamp_insert
    BEFORE INSERT ON product_lifecycle
    FOR EACH ROW
    EXECUTE FUNCTION set_initial_timestamps();

CREATE TRIGGER product_lifecycle_timestamp_update
    BEFORE UPDATE ON product_lifecycle
    FOR EACH ROW
    EXECUTE FUNCTION update_timestamp();

-- Validate lifecycle dates
CREATE OR REPLACE FUNCTION validate_lifecycle_dates()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.status_end_date IS NOT NULL AND NEW.status_end_date <= NEW.status_start_date THEN
        RAISE EXCEPTION 'Lifecycle status end date must be after start date';
END IF;
RETURN NEW;
END;
$$ language 'plpgsql';

CREATE TRIGGER product_lifecycle_dates
    BEFORE INSERT OR UPDATE ON product_lifecycle
                         FOR EACH ROW
                         EXECUTE FUNCTION validate_lifecycle_dates();

-- === PRODUCT_LIMIT TABLE TRIGGERS ===
CREATE TRIGGER product_limit_timestamp_insert
    BEFORE INSERT ON product_limit
    FOR EACH ROW
    EXECUTE FUNCTION set_initial_timestamps();

CREATE TRIGGER product_limit_timestamp_update
    BEFORE UPDATE ON product_limit
    FOR EACH ROW
    EXECUTE FUNCTION update_timestamp();

-- Validate limit dates
CREATE OR REPLACE FUNCTION validate_limit_dates()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.expiry_date IS NOT NULL AND NEW.expiry_date <= NEW.effective_date THEN
        RAISE EXCEPTION 'Limit expiry date must be after effective date';
END IF;
RETURN NEW;
END;
$$ language 'plpgsql';

CREATE TRIGGER product_limit_dates
    BEFORE INSERT OR UPDATE ON product_limit
                         FOR EACH ROW
                         EXECUTE FUNCTION validate_limit_dates();

-- === PRODUCT_BUNDLE TABLE TRIGGERS ===
CREATE TRIGGER product_bundle_timestamp_insert
    BEFORE INSERT ON product_bundle
    FOR EACH ROW
    EXECUTE FUNCTION set_initial_timestamps();

CREATE TRIGGER product_bundle_timestamp_update
    BEFORE UPDATE ON product_bundle
    FOR EACH ROW
    EXECUTE FUNCTION update_timestamp();

-- === PRODUCT_BUNDLE_ITEM TABLE TRIGGERS ===
CREATE TRIGGER product_bundle_item_timestamp_insert
    BEFORE INSERT ON product_bundle_item
    FOR EACH ROW
    EXECUTE FUNCTION set_initial_timestamps();

CREATE TRIGGER product_bundle_item_timestamp_update
    BEFORE UPDATE ON product_bundle_item
    FOR EACH ROW
    EXECUTE FUNCTION update_timestamp();

-- === PRODUCT_RELATIONSHIP TABLE TRIGGERS ===
CREATE TRIGGER product_relationship_timestamp_insert
    BEFORE INSERT ON product_relationship
    FOR EACH ROW
    EXECUTE FUNCTION set_initial_timestamps();

CREATE TRIGGER product_relationship_timestamp_update
    BEFORE UPDATE ON product_relationship
    FOR EACH ROW
    EXECUTE FUNCTION update_timestamp();

-- Prevent self-relationships
CREATE OR REPLACE FUNCTION prevent_self_relationship()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.product_id = NEW.related_product_id THEN
        RAISE EXCEPTION 'A product cannot have a relationship with itself';
END IF;
RETURN NEW;
END;
$$ language 'plpgsql';

CREATE TRIGGER product_self_relationship
    BEFORE INSERT OR UPDATE ON product_relationship
                         FOR EACH ROW
                         EXECUTE FUNCTION prevent_self_relationship();

-- === PRODUCT_VERSION TABLE TRIGGERS ===
CREATE TRIGGER product_version_timestamp_insert
    BEFORE INSERT ON product_version
    FOR EACH ROW
    EXECUTE FUNCTION set_initial_timestamps();

CREATE TRIGGER product_version_timestamp_update
    BEFORE UPDATE ON product_version
    FOR EACH ROW
    EXECUTE FUNCTION update_timestamp();

-- === PRODUCT_LOCALIZATION TABLE TRIGGERS ===
CREATE TRIGGER product_localization_timestamp_insert
    BEFORE INSERT ON product_localization
    FOR EACH ROW
    EXECUTE FUNCTION set_initial_timestamps();

CREATE TRIGGER product_localization_timestamp_update
    BEFORE UPDATE ON product_localization
    FOR EACH ROW
    EXECUTE FUNCTION update_timestamp();

-- === PRODUCT_PRICING_LOCALIZATION TABLE TRIGGERS ===
CREATE TRIGGER product_pricing_localization_timestamp_insert
    BEFORE INSERT ON product_pricing_localization
    FOR EACH ROW
    EXECUTE FUNCTION set_initial_timestamps();

CREATE TRIGGER product_pricing_localization_timestamp_update
    BEFORE UPDATE ON product_pricing_localization
    FOR EACH ROW
    EXECUTE FUNCTION update_timestamp();

-- === FEE_STRUCTURE TABLE TRIGGERS ===
CREATE TRIGGER fee_structure_timestamp_insert
    BEFORE INSERT ON fee_structure
    FOR EACH ROW
    EXECUTE FUNCTION set_initial_timestamps();

CREATE TRIGGER fee_structure_timestamp_update
    BEFORE UPDATE ON fee_structure
    FOR EACH ROW
    EXECUTE FUNCTION update_timestamp();

-- === FEE_COMPONENT TABLE TRIGGERS ===
CREATE TRIGGER fee_component_timestamp_insert
    BEFORE INSERT ON fee_component
    FOR EACH ROW
    EXECUTE FUNCTION set_initial_timestamps();

CREATE TRIGGER fee_component_timestamp_update
    BEFORE UPDATE ON fee_component
    FOR EACH ROW
    EXECUTE FUNCTION update_timestamp();

-- === PRODUCT_FEE_STRUCTURE TABLE TRIGGERS ===
CREATE TRIGGER product_fee_structure_timestamp_insert
    BEFORE INSERT ON product_fee_structure
    FOR EACH ROW
    EXECUTE FUNCTION set_initial_timestamps();

CREATE TRIGGER product_fee_structure_timestamp_update
    BEFORE UPDATE ON product_fee_structure
    FOR EACH ROW
    EXECUTE FUNCTION update_timestamp();

-- === FEE_APPLICATION_RULE TABLE TRIGGERS ===
CREATE TRIGGER fee_application_rule_timestamp_insert
    BEFORE INSERT ON fee_application_rule
    FOR EACH ROW
    EXECUTE FUNCTION set_initial_timestamps();

CREATE TRIGGER fee_application_rule_timestamp_update
    BEFORE UPDATE ON fee_application_rule
    FOR EACH ROW
    EXECUTE FUNCTION update_timestamp();

-- Validate fee rule dates
CREATE OR REPLACE FUNCTION validate_fee_rule_dates()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.expiry_date IS NOT NULL AND NEW.expiry_date <= NEW.effective_date THEN
        RAISE EXCEPTION 'Fee rule expiry date must be after effective date';
END IF;
RETURN NEW;
END;
$$ language 'plpgsql';

CREATE TRIGGER fee_rule_dates
    BEFORE INSERT OR UPDATE ON fee_application_rule
                         FOR EACH ROW
                         EXECUTE FUNCTION validate_fee_rule_dates();