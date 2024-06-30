--liquibase formatted sql

--changeset metlushkoaa:1
CREATE TABLE article (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    author VARCHAR(100) NOT NULL,
    content TEXT NOT NULL,
    publish_date DATE NOT NULL
);









