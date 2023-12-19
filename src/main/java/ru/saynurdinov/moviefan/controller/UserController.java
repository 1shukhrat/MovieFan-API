package ru.saynurdinov.moviefan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.saynurdinov.moviefan.DTO.MessageResponse;
import ru.saynurdinov.moviefan.DTO.UserInfoDTO;
import ru.saynurdinov.moviefan.service.UserService;


@RestController
@RequestMapping("/api/v2/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/get")
    public UserInfoDTO get() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserInfoDTO) authentication.getPrincipal();
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteUser(@PathVariable("id") long userId) {
        userService.removeAccount(userId);
        return ResponseEntity.ok(new MessageResponse("Аккаунт успешно удален"));
    }
}
