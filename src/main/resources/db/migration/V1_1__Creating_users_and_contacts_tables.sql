CREATE TABLE IF NOT EXISTS users
(
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(40),
    password VARCHAR(255),
    role VARCHAR(10)
    );

CREATE TABLE IF NOT EXISTS contact_type
(
    id BIGINT PRIMARY KEY ,
    description  VARCHAR(40),
    contact_type VARCHAR(40)
);

CREATE TABLE IF NOT EXISTS contacts
(
    id BIGINT PRIMARY KEY,
    first_name VARCHAR(40) NOT NULL,
    last_name VARCHAR(40) NOT NULL,
    address VARCHAR(40),
    phone_number VARCHAR(40),
    user_id BIGINT references users(id),
    contact_type_id BIGINT references contact_type(id)
    );
