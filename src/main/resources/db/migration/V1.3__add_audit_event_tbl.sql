-- create audit_events table
CREATE TABLE IF NOT EXISTS audit_events (
    "id" SERIAL PRIMARY KEY,
    "created_on" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    "timestamp" VARCHAR(40),
    "principal" VARCHAR(100),
    "event_type" VARCHAR(100),
    "event_data" JSON,
    "status_id" SMALLINT REFERENCES statuses("id") ON DELETE SET NULL
);