-- liquibase formatted sql
-- changeset cascade:2
CREATE TABLE IF NOT EXISTS products (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(255) UNIQUE NOT NULL,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    image VARCHAR(255),
    category VARCHAR(255),
    price DOUBLE PRECISION CHECK (price >= 0.0),
    quantity INTEGER CHECK (quantity >= 0),
    internal_reference VARCHAR(255),
    shell_id BIGINT,
    inventory_status VARCHAR(32),
    rating DOUBLE PRECISION CHECK (rating >= 0.0 AND rating <= 5.0),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Optionally, add unique constraints
ALTER TABLE products ADD CONSTRAINT uc_products_code UNIQUE (code);
