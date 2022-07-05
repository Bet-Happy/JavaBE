DROP TABLE IF EXISTS resources;

CREATE TABLE resources(
    id serial PRIMARY KEY,
    name text NOT NULL,
    xp integer NOT NULL,
    tpa integer NOT NULL
);