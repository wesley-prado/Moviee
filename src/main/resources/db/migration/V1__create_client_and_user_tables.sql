CREATE TABLE clients
(
  id            UUID         NOT NULL,
  client_id     VARCHAR(100) NOT NULL,
  client_secret VARCHAR(255) NOT NULL,
  redirect_uri  VARCHAR(255) NOT NULL,
  client_name   VARCHAR(255) NOT NULL,
  CONSTRAINT pk_clients PRIMARY KEY (id)
);

CREATE TABLE users
(
  id            UUID         NOT NULL,
  username      VARCHAR(20)  NOT NULL,
  email         VARCHAR(255) NOT NULL,
  password      VARCHAR(255) NOT NULL,
  document      VARCHAR(20),
  document_type VARCHAR(10),
  role          VARCHAR(25)  NOT NULL,
  status        VARCHAR(25)  NOT NULL,
  CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE clients
  ADD CONSTRAINT uc_clients_clientid UNIQUE (client_id);

ALTER TABLE users
  ADD CONSTRAINT uc_users_document UNIQUE (document);

ALTER TABLE users
  ADD CONSTRAINT uc_users_email UNIQUE (email);

ALTER TABLE users
  ADD CONSTRAINT uc_users_username UNIQUE (username);