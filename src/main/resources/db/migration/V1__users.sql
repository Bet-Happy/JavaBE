DROP TABLE IF EXISTS users;

CREATE TABLE users (
  id bigserial PRIMARY KEY,
  username varchar(50) NOT NULL UNIQUE,
  password varchar(60) NOT NULL,
  enabled BOOLEAN NOT NULL
 );