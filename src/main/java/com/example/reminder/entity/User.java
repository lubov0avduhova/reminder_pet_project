package com.example.reminder.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.List;

@Entity
@Table(name = "\"user\"")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Имя пользователя не должно быть пустым")
    private String username;

    @Size(min = 5, max = 32, message = "Телеграм Id должен содержать более 5 символов")
    @Column(name = "telegram_id")
    //todo нужно ли ставить проверку на null? может пользователь не получать уведомления
    private String telegramId;

    @Email(message = "Неправильно введена почта")
    //todo нужно ли ставить проверку на null? может пользователь не получать уведомления
    private String email;

    //todo тут точно all или отдельно прописать какие нужно?
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Reminder> reminders;
}
