package com.example.reminder.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
//todo может изменить название таблицы? в postgres это таблица для юзеров и постоянно нужно указывать имя схемы
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String username;

    @Min(value = 5, message = "Телеграм Id должен содержать более 5 символов")
    @Max(value = 32, message = "Телеграм Id должен содержать не более 32 символов")
    @Column(name = "telegram_id")
    private String telegramId;

    @Email(message = "Неправильно введена почта")
    private String email;

    //todo тут точно all или отдельно прописать какие нужно?
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reminder> reminders;
}
