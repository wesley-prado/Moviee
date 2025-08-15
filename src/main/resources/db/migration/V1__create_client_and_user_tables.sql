CREATE TABLE client_tb
(
  id            UUID         NOT NULL,
  client_id     VARCHAR(100) NOT NULL,
  client_secret VARCHAR(255) NOT NULL,
  redirect_uri  VARCHAR(255) NOT NULL,
  client_name   VARCHAR(255) NOT NULL,
  CONSTRAINT pk_client_tb PRIMARY KEY (id)
);

CREATE TABLE user_tb
(
  id            UUID         NOT NULL,
  username      VARCHAR(20)  NOT NULL,
  email         VARCHAR(255) NOT NULL,
  password      VARCHAR(255) NOT NULL,
  document      VARCHAR(20),
  document_type VARCHAR(10)  NOT NULL,
  role          VARCHAR(25)  NOT NULL,
  status        VARCHAR(25)  NOT NULL,
  CONSTRAINT pk_user_tb PRIMARY KEY (id)
);

ALTER TABLE client_tb
  ADD CONSTRAINT uc_client_tb_clientid UNIQUE (client_id);

ALTER TABLE user_tb
  ADD CONSTRAINT uc_user_tb_document UNIQUE (document);

ALTER TABLE user_tb
  ADD CONSTRAINT uc_user_tb_email UNIQUE (email);

ALTER TABLE user_tb
  ADD CONSTRAINT uc_user_tb_username UNIQUE (username);