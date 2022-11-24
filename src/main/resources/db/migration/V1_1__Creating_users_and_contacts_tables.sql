CREATE TABLE IF NOT EXISTS users
(
    id BIGINT PRIMARY KEY,
    email VARCHAR(40) unique,
    password VARCHAR(255),
    role VARCHAR(10)
    );

CREATE TABLE IF NOT EXISTS contacts
(
    id BIGINT PRIMARY KEY,
    first_name VARCHAR(40) NOT NULL,
    last_name VARCHAR(40) NOT NULL,
    address VARCHAR(40),
    phone_number VARCHAR(40),
    user_id BIGINT references users(id)
    );