CREATE TABLE IF NOT EXISTS users
(
    id       BIGSERIAL PRIMARY KEY,
    login    VARCHAR(128) UNIQUE NOT NULL,
    password VARCHAR(255)        NOT NULL CHECK ( length(password) > 5 )
);

CREATE INDEX IF NOT EXISTS login_index ON users (login);

CREATE TABLE IF NOT EXISTS sessions
(
    id         UUID PRIMARY KEY,
    user_id    BIGINT    NOT NULL REFERENCES users (id),
    expires_at TIMESTAMP NOT NULL
);

CREATE INDEX IF NOT EXISTS expires_at_index ON sessions (expires_at);

CREATE TABLE IF NOT EXISTS locations
(
    id        BIGSERIAL PRIMARY KEY,
    user_id   BIGINT REFERENCES users (id),
    name      VARCHAR(128) NOT NULL,
    state     VARCHAR(128) NOT NULL,
    latitude  NUMERIC      NOT NULL,
    longitude NUMERIC      NOT NULL,
    UNIQUE (user_id, latitude, longitude)
);

CREATE INDEX IF NOT EXISTS locations_index ON locations (latitude, longitude);