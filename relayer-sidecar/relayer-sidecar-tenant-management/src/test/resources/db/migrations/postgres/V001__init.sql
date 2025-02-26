CREATE TABLE message_source (
    identifier VARCHAR(256) PRIMARY KEY,
    state VARCHAR(128),
    configuration TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP
);
