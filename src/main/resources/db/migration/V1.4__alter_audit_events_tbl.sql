ALTER TABLE audit_events DROP COLUMN event_data;
ALTER TABLE audit_events DROP COLUMN event_type;
ALTER TABLE audit_events DROP COLUMN timestamp;

CREATE TABLE IF NOT EXISTS audit_event_types (
    "id" SERIAL PRIMARY KEY,
    "name" VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS audit_event_data (
    "audit_event_id" INTEGER PRIMARY KEY REFERENCES audit_events("id") ON DELETE CASCADE,
    "remote_address" VARCHAR(50),
    "session_id" VARCHAR(50),
    "data_type" VARCHAR,
    "event_message" VARCHAR
);

ALTER TABLE audit_events ADD COLUMN event_type_id INTEGER REFERENCES audit_event_types("id") ON DELETE SET NULL;