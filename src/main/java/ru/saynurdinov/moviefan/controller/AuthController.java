package ru.saynurdinov.moviefan.controller;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.saynurdinov.moviefan.DTO.*;
import ru.saynurdinov.moviefan.security.JwtUtils;
import ru.saynurdinov.moviefan.service.AuthService;

@Controller
@RequestMapping("/api/v2/auth")
public class AuthController {

    private final JwtUtils jwtUtils;

    private final AuthService authService;

    @Autowired
    public AuthController(JwtUtils jwtUtils, AuthService authService) {
        this.jwtUtils = jwtUtils;
        this.authService = authService;
    }

    @PostMapping("/signIn")
    public ResponseEntity<?> logIn(@Valid @RequestBody LoginDTO loginDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Неверный формат введенных данных"));
        }
        try {
            AuthResponse authResponse =  authService.signIn(loginDTO);
            return ResponseEntity.ok(authResponse);
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new MessageResponse("Неправильный логин или пароль"), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@Valid @RequestBody RegisterDTO registerDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Неверный формат введенных данных"));
        }
        try {
            return ResponseEntity.ok(authService.signUp(registerDTO));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }


}
