package ru.yandex.practicum.filmorate.controller;

import java.util.Collection;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.yandex.practicum.filmorate.exception.NoContentException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User addUser(@RequestBody @Valid User user) {
        log.info("User to add: " + user.toString());
        User added = userService.add(user);
        log.info("User added: " + added.toString());
        return added;
    }

    @PutMapping
    public User updateUser(@RequestBody @Valid User user) {
        log.info("User to update: " + user.toString());
        User update = userService.update(user);
        log.info("User updated: " + update.toString());

        return update;
    }

    @DeleteMapping("/{id}")
    public User deleteUser(@PathVariable Long id) {
        return userService.delete(id);
    }

    @GetMapping
    public Collection<User> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getById(id);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable Long id, @PathVariable Long friendId) {
        userService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable Long id, @PathVariable Long friendId) throws NoContentException {
        userService.removeFriend(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public Collection<User> getFriends(@PathVariable Long id) {
        return userService.getFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public Collection<User> getCommonFriends(@PathVariable Long id, @PathVariable Long otherId) {
        return userService.getAllCommonFriends(id, otherId);
    }
}
