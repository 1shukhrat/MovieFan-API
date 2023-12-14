package ru.saynurdinov.moviefan.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.saynurdinov.moviefan.DTO.UserInfoDTO;


@RestController
@RequestMapping("/api/v2/users")
public class UserController {


    @GetMapping("/get")
    public UserInfoDTO get() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserInfoDTO) authentication.getPrincipal();
    }
}
