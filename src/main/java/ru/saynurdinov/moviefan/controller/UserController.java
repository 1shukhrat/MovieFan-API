package ru.saynurdinov.moviefan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import ru.saynurdinov.moviefan.util.MessageResponse;
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

    @DeleteMapping("/remove")
    public ResponseEntity<?> deleteUser() {
        try {
            userService.removeAccount();
            return ResponseEntity.ok(new MessageResponse("Аккаунт успешно удален"));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse(e.getMessage()));
        }

    }
}
