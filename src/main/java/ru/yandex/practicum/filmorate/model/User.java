package ru.yandex.practicum.filmorate.model;

import java.time.LocalDate;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import ru.yandex.practicum.filmorate.annotation.Login;

@Data
@Valid
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class User {
    private int id;
    private String name;
    @Login
    private String login;
    @Email
    @NotBlank
    private String email;
    @PastOrPresent
    private LocalDate birthdate;

    public User(String name, String login, String email, LocalDate birthday) {
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthdate = birthday;
    }

    public String getName() {
        return name.isBlank() ? login : name;
    }
}
