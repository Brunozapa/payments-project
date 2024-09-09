CREATE TABLE IF NOT EXISTS account(
    "id" CHAR(26) PRIMARY KEY,
    "name" VARCHAR(250) NOT NULL,
    "cpf" VARCHAR(50) NOT NULL,
    "birthdate" DATE NOT NULL,
    "phone" VARCHAR(50),
    "email" VARCHAR(100),
    "balance" NUMERIC(10,2) NOT NULL,
    "updated_at" TIMESTAMP WITHOUT TIME ZONE,
    "created_at" TIMESTAMP WITHOUT TIME ZONE
);