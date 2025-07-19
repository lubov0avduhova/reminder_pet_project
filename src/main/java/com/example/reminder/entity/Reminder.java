package com.example.reminder.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "reminder")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
//todo можно ли использовать @Data?
public class Reminder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 255, message = "Краткое описание должно быть больше 255 символов")
    @NotEmpty(message = "Краткое описание не должно быть пустым")
    private String title;

    @Size(max = 4096, message = "Полное описание должно быть больше 4096 символов")
    private String description;

    @Future(message = "Неправильно введена дата")
    @NotNull(message = "Дата не должна быть пустой")
    private LocalDateTime remind;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull(message = "ID пользователя не должен быть пустым")
    private User user;
}
