CREATE TABLE reminder
(
    id          SERIAL PRIMARY KEY NOT NULL,
    title       VARCHAR(255),
    description VARCHAR(4096),
    remind      TIMESTAMP,
    app_user_id BIGINT
);

ALTER TABLE reminder
    ADD CONSTRAINT FK_REMINDER_ON_APP_USER
        FOREIGN KEY (app_user_id)
            REFERENCES app_user (id);
