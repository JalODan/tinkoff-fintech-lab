CREATE TABLE IF NOT EXISTS request (
        id UUID PRIMARY KEY,
        original_text TEXT NOT NULL,
        target_language_code VARCHAR(2) NOT NULL,
        translated_text TEXT NOT NULL,
        ip_address VARCHAR(40) NOT NULL,
        created_at TIMESTAMP NOT NULL DEFAULT NOW()
);