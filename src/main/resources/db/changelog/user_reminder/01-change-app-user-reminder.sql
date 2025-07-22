CREATE TABLE app_user_reminders (
                                app_user_id BIGINT NOT NULL,
                                reminders_id BIGINT NOT NULL
);

ALTER TABLE app_user_reminders
    ADD CONSTRAINT uc_app_user_reminders_reminders
        UNIQUE (reminders_id);

ALTER TABLE app_user_reminders
    ADD CONSTRAINT fk_user_on_reminder
        FOREIGN KEY (reminders_id)
            REFERENCES reminder(id);

ALTER TABLE app_user_reminders
    ADD CONSTRAINT fk_app_user_on_user
        FOREIGN KEY (app_user_id)
            REFERENCES app_user(id);
