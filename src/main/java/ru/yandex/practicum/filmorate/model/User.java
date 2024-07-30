package ru.yandex.practicum.filmorate.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import ru.yandex.practicum.filmorate.annotation.Login;

@Data
@Valid
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode(of = {"id", "login", "email"})
public class User {
    private Long id;
    private String name;
    @Login
    private String login;
    @Email
    @NotBlank
    private String email;
    @PastOrPresent(message = "birthday should be past or present.")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    public User(String name, String login, String email, LocalDate birthday) {
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
    }

    public String getName() {
        return name.isBlank() ? login : name;
    }
}
