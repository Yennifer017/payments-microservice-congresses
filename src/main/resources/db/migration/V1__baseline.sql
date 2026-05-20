
CREATE TABLE constants (
    id SERIAL PRIMARY KEY,
    value DOUBLE PRECISION NOT NULL,
    name VARCHAR(255) NOT NULL,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL,
    deleted_at TIMESTAMP NULL,
    deactivate_at TIMESTAMP NULL
);

INSERT INTO constants (name, value, created_at, updated_at, deleted_at, deactivate_at)
VALUES ('comision', 0.05, NOW(), NOW(), NULL, NULL);

CREATE TABLE wallet (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL UNIQUE,
    currency DOUBLE PRECISION NOT NULL,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL,
    deleted_at TIMESTAMP NULL,
    deactivate_at TIMESTAMP NULL
);

CREATE TABLE payment (
    id SERIAL PRIMARY KEY,
    wallet_id INTEGER NOT NULL,
    congress_id INTEGER NOT NULL,
    description VARCHAR(255) NULL,
    amount DOUBLE PRECISION NOT NULL,
    commission DOUBLE PRECISION NOT NULL,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL,
    deleted_at TIMESTAMP NULL,
    deactivate_at TIMESTAMP NULL,

    CONSTRAINT fk_payment_wallet FOREIGN KEY (wallet_id)
        REFERENCES wallet (id) ON DELETE RESTRICT
);