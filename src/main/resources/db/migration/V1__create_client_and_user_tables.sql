CREATE TABLE client
(
  id           UUID NOT NULL,
  client_id    VARCHAR(255),
  redirect_uri VARCHAR(255),
  client_name  VARCHAR(255),
  CONSTRAINT pk_client PRIMARY KEY (id)
);

CREATE TABLE user_tb
(
  id            UUID         NOT NULL,
  username      VARCHAR(255),
  email         VARCHAR(255),
  password      VARCHAR(255),
  document      VARCHAR(255),
  document_type VARCHAR(255) NOT NULL,
  role          VARCHAR(255),
  status        VARCHAR(255) NOT NULL,
  CONSTRAINT pk_user_tb PRIMARY KEY (id)
);