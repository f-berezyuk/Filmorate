package ru.yandex.practicum.filmorate.model;

import java.time.LocalDate;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;

import ru.yandex.practicum.filmorate.annotation.Login;

@Data
@Valid
@AllArgsConstructor
public class User {
    private Long id;
    @Email
    @NotBlank
    private String email;
    @Login
    private String login;
    private String name;
    @PastOrPresent
    private LocalDate birthday;

    public User(String email, String login, String name, LocalDate birthday) {
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
    }

    public String getName() {
        return name.isBlank() ? login : name;
    }
}
