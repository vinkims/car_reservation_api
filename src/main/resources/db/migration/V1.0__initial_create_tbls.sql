-- statuses
CREATE TABLE IF NOT EXISTS statuses (
    "id" SMALLSERIAL PRIMARY KEY,
    "created_on" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    "name" VARCHAR(50) NOT NULL UNIQUE,
    "description" VARCHAR
);

-- roles
CREATE TABLE IF NOT EXISTS roles (
    "id" SMALLSERIAL PRIMARY KEY,
    "created_on" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    "name" VARCHAR(50) NOT NULL UNIQUE,
    "description" VARCHAR
);

-- users
CREATE TABLE IF NOT EXISTS users (
    "id" SERIAL PRIMARY KEY,
    "created_on" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    "last_active_on" TIMESTAMP,
    "modified_on" TIMESTAMP,
    "first_name" VARCHAR(50),
    "middle_name" VARCHAR(50),
    "last_name" VARCHAR(50),
    "passcode" VARCHAR,
    "age" INTEGER,
    "role_id" SMALLINT REFERENCES roles("id") ON DELETE SET NULL,
    "status_id" SMALLINT REFERENCES statuses("id") ON DELETE SET NULL
);

-- contact_types
CREATE TABLE IF NOT EXISTS contact_types (
    "id" SMALLSERIAL PRIMARY KEY,
    "name" VARCHAR(50) NOT NULL UNIQUE,
    "created_on" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    "description" VARCHAR,
    "regex_value" VARCHAR
);

-- user_contacts
CREATE TABLE IF NOT EXISTS user_contacts (
    "user_id" INTEGER NOT NULL REFERENCES users("id") ON DELETE CASCADE,
    "created_on" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    "contact_value" VARCHAR(100) NOT NULL UNIQUE,
    "contact_type_id" SMALLINT REFERENCES contact_types("id") ON DELETE SET NULL
);

-- civil_identity_types
CREATE TABLE IF NOT EXISTS civil_identity_types (
    "id" SMALLSERIAL PRIMARY KEY,
    "name" VARCHAR(50) NOT NULL UNIQUE,
    "created_on" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    "description" VARCHAR
);

-- user_civil_identities
CREATE TABLE IF NOT EXISTS user_civil_identities (
    "user_id" INTEGER NOT NULL REFERENCES users("id") ON DELETE CASCADE,
    "created_on" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    "civil_identity_value" VARCHAR(50) NOT NULL PRIMARY KEY,
    "civil_identity_type_id" SMALLINT REFERENCES civil_identity_types("id") ON DELETE SET NULL
);

-- vehicle_models
CREATE TABLE IF NOT EXISTS vehicle_models (
    "id" SMALLSERIAL PRIMARY KEY,
    "created_on" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    "modified_on" TIMESTAMP,
    "vehicle_make" VARCHAR(50),
    "vehicle_model" VARCHAR(50)
);

-- transmission_types
CREATE TABLE IF NOT EXISTS transmission_types (
    "id" SMALLSERIAL PRIMARY KEY,
    "created_on" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    "name" VARCHAR(50) NOT NULL UNIQUE,
    "description" VARCHAR
);

-- fuel_types
CREATE TABLE IF NOT EXISTS fuel_types (
    "id" SMALLSERIAL PRIMARY KEY,
    "created_on" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    "name" VARCHAR(50),
    "description" VARCHAR
);

-- vehicles
CREATE TABLE IF NOT EXISTS vehicles (
    "id" SERIAL PRIMARY KEY,
    "created_on" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    "last_active_on" TIMESTAMP,
    "modified_on" TIMESTAMP,
    "registration_number" VARCHAR(50),
    "vehicle_model_id" SMALLINT REFERENCES vehicle_models("id") ON DELETE SET NULL,
    "color" VARCHAR(50),
    "engine_capacity" INTEGER,
    "booking_amount" NUMERIC(11, 4),
    "transmission_type_id" SMALLINT REFERENCES transmission_types("id") ON DELETE SET NULL,
    "fuel_type_id" SMALLINT REFERENCES fuel_types("id") ON DELETE SET NULL,
    "status_id" SMALLINT REFERENCES statuses("id") ON DELETE SET NULL
);

-- reviews
CREATE TABLE IF NOT EXISTS reviews (
    "id" SERIAL PRIMARY KEY,
    "created_on" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    "modified_on" TIMESTAMP,
    "vehicle_id" INTEGER REFERENCES vehicles("id") ON DELETE SET NULL,
    "review" INTEGER,
    "comment" VARCHAR,
    "status_id" SMALLINT REFERENCES statuses("id") ON DELETE SET NULL
);

-- countries
CREATE TABLE IF NOT EXISTS countries (
    "id" SMALLSERIAL PRIMARY KEY,
    "name" VARCHAR(50) NOT NULL UNIQUE,
    "currency" CHAR(2)
);

-- regions
CREATE TABLE IF NOT EXISTS regions (
    "id" SMALLSERIAL PRIMARY KEY,
    "name" VARCHAR(50) NOT NULL,
    "country_id" SMALLINT REFERENCES countries("id") ON DELETE SET NULL
);

-- municipalities
CREATE TABLE IF NOT EXISTS municipalities (
    "id" SMALLSERIAL PRIMARY KEY,
    "name" VARCHAR(50) NOT NULL,
    "region_id" SMALLINT REFERENCES regions("id") ON DELETE SET NULL
);

-- areas
CREATE TABLE IF NOT EXISTS areas (
    "id" SMALLSERIAL PRIMARY KEY,
    "name" VARCHAR(50) NOT NULL,
    "municipality_id" SMALLINT REFERENCES municipalities("id") ON DELETE SET NULL
);

-- locations
CREATE TABLE IF NOT EXISTS locations (
    "id" SMALLSERIAL PRIMARY KEY,
    "created_on" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    "name" VARCHAR NOT NULL,
    "area_id" SMALLINT REFERENCES areas("id") ON DELETE SET NULL,
    "additional_info" VARCHAR
);

-- reservations
CREATE TABLE IF NOT EXISTS reservations (
    "id" SERIAL PRIMARY KEY,
    "created_on" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    "modified_on" TIMESTAMP,
    "user_id" INTEGER REFERENCES users("id") ON DELETE SET NULL,
    "vehicle_id" INTEGER REFERENCES vehicles("id") ON DELETE SET NULL,
    "pickup_location_id" SMALLINT REFERENCES locations("id") ON DELETE SET NULL,
    "dropoff_location_id" SMALLINT REFERENCES locations("id") ON DELETE SET NULL,
    "pickup_date" DATE,
    "dropoff_date" DATE,
    "status_id" SMALLINT REFERENCES statuses("id") ON DELETE SET NUll
);

-- payment_channels
CREATE TABLE IF NOT EXISTS payment_channels (
    "id" SMALLSERIAL PRIMARY KEY,
    "created_on" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    "name" VARCHAR(50) NOT NULL UNIQUE,
    "description" VARCHAR
);

-- transaction_types
CREATE TABLE IF NOT EXISTS transaction_types (
    "id" SMALLSERIAL PRIMARY KEY,
    "created_on" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    "name" VARCHAR(50) NOT NULL UNIQUE,
    "description" VARCHAR
);

-- payments
CREATE TABLE IF NOT EXISTS payments (
    "id" SERIAL PRIMARY KEY,
    "created_on" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    "external_transaction_id" VARCHAR NOT NULL UNIQUE,
    "amount" NUMERIC(11,4),
    "description" VARCHAR,
    "transaction_type_id" SMALLINT REFERENCES transaction_types("id") ON DELETE SET NULL,
    "payment_channel_id" SMALLINT REFERENCES payment_channels("id"),
    "reference" VARCHAR,
    "user_id" INTEGER REFERENCES users("id") ON DELETE SET NULL,
    "status_id" SMALLINT REFERENCES statuses("id") ON DELETE SET NULL
);
