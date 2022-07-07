DROP TABLE IF EXISTS inventory;

CREATE TABLE inventory(
id SERIAL PRIMARY KEY,
characters_id INTEGER REFERENCES characters(id) NOT NULL,
slot_number INTEGER NOT NULL,
amount INTEGER NOT NULL
);