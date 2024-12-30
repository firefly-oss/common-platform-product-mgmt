-- V8__Create_cast_enums.sql

-- Create casts for all enum types
CREATE CAST (varchar AS product_type_enum) WITH INOUT AS IMPLICIT;
CREATE CAST (varchar AS product_status_enum) WITH INOUT AS IMPLICIT;
CREATE CAST (varchar AS feature_type_enum) WITH INOUT AS IMPLICIT;
CREATE CAST (varchar AS pricing_type_enum) WITH INOUT AS IMPLICIT;
CREATE CAST (varchar AS doc_type_enum) WITH INOUT AS IMPLICIT;
CREATE CAST (varchar AS lifecycle_status_enum) WITH INOUT AS IMPLICIT;
CREATE CAST (varchar AS limit_type_enum) WITH INOUT AS IMPLICIT;
CREATE CAST (varchar AS time_period_enum) WITH INOUT AS IMPLICIT;
CREATE CAST (varchar AS bundle_status_enum) WITH INOUT AS IMPLICIT;
CREATE CAST (varchar AS relationship_type_enum) WITH INOUT AS IMPLICIT;
CREATE CAST (varchar AS fee_structure_type_enum) WITH INOUT AS IMPLICIT;
CREATE CAST (varchar AS fee_type_enum) WITH INOUT AS IMPLICIT;
CREATE CAST (varchar AS fee_unit_enum) WITH INOUT AS IMPLICIT;