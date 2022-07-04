DROP TABLE IF EXISTS character;

CREATE TABLE character(
    id serial PRIMARY KEY,
    name text NOT NULL,
    mining bigint NOT NULL
);