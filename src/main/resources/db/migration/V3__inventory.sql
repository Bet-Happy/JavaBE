DROP TABLE IF EXISTS inventory;

CREATE TABLE inventory(
id SERIAL PRIMARY KEY,
characters_id INTEGER REFERENCES characters(id) NOT NULL,
resource_id INTEGER REFERENCES resource(id) NOT NULL,
amount INTEGER NOT NULL
);