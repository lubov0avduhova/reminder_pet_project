CREATE TABLE "app_user"
(
    id          SERIAL PRIMARY KEY NOT NULL,
    username    VARCHAR(255),
    telegram_id VARCHAR(255),
    email       VARCHAR(255)
);
