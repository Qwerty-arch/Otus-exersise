DROP TABLE IF EXISTS authors;
CREATE TABLE authors (
    id BIGSERIAL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) UNIQUE
);

DROP TABLE IF EXISTS genres;
CREATE TABLE genres (
    id BIGSERIAL AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(255) UNIQUE
);

DROP TABLE IF EXISTS books;
CREATE TABLE books(
    id BIGSERIAL AUTO_INCREMENT PRIMARY KEY,
    author_id BIGINT REFERENCES authors (id),
    genre_id BIGINT REFERENCES genres (id),
    title VARCHAR(255)
);
