-- blacklist_tokens: contains deactivated tokens
CREATE TABLE IF NOT EXISTS blacklist_tokens (
    "id" SERIAL PRIMARY KEY,
    "user_id" INTEGER REFERENCES users("id") ON DELETE SET NULL,
    "token_hash" BIGINT NOT NULL UNIQUE,
    "created_on" TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);