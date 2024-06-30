package ru.yandex.practicum.filmorate.controller;

import java.util.Collection;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    UserService userService = new UserService();

    @PostMapping
    public User addUser(@RequestBody @Valid User user) {
        return userService.add(user);
    }

    @PatchMapping
    public User updateUser(@RequestBody @Valid User user, Long id) {
        return userService.update(id, user);
    }

    @GetMapping
    public Collection<User> getAll() {
        return userService.getAll();
    }
}
