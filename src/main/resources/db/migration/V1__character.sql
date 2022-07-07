DROP TABLE IF EXISTS characters;

CREATE TABLE characters(
    id serial PRIMARY KEY,
    name text NOT NULL,
    mining integer DEFAULT 0
);