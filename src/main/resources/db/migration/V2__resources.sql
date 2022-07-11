DROP TABLE IF EXISTS resource;

CREATE TABLE resource(
    id serial PRIMARY KEY,
    name text NOT NULL,
    xp integer NOT NULL,
    tpa integer NOT NULL
);