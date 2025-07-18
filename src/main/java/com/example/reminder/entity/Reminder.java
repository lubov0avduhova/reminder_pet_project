package com.example.reminder.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "reminder")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//todo можно ли использовать @Data?
public class Reminder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Max(value = 255, message = "Краткое описание должно быть больше 255 символов")
    private String title;

    @Max(value = 4096, message = "Полное описание должно быть больше 4096 символов")
    private String description;

    @Future(message = "Неправильно введена дата")
    private LocalDateTime remind;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;
}
