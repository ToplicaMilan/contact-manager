CREATE TABLE IF NOT EXISTS users
(
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(40),
    password VARCHAR(255),
    role VARCHAR(10)
    );

CREATE TABLE IF NOT EXISTS contact_type
(
    id BIGSERIAL PRIMARY KEY,
    description  VARCHAR(40),
    type VARCHAR(40)
);

CREATE TABLE IF NOT EXISTS contact
(
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(40),
    last_name VARCHAR(40),
    address VARCHAR(40),
    phone_number VARCHAR(40),
    user_id BIGINT references users(id),
    contact_type_id BIGINT references contact_type(id)
    );
