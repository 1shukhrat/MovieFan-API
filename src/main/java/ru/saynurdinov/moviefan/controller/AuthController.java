package ru.saynurdinov.moviefan.controller;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.saynurdinov.moviefan.DTO.*;
import ru.saynurdinov.moviefan.security.JwtUtils;
import ru.saynurdinov.moviefan.service.AuthService;

@Controller
@RequestMapping("/api/v2/")
public class AuthController {

    private final JwtUtils jwtUtils;

    private final AuthService authService;

    @Autowired
    public AuthController(JwtUtils jwtUtils, AuthService authService) {
        this.jwtUtils = jwtUtils;
        this.authService = authService;
    }

    @PostMapping("/signIn")
    public ResponseEntity<?> logIn(@Valid @RequestBody LoginDTO loginDTO) {
        AuthResponse authResponse =  authService.signIn(loginDTO);
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, authResponse.getJwtCookie())
                .body(new UserInfoDTO(authResponse.getId(), authResponse.getUsername()));
    }

    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@Valid @RequestBody RegisterDTO registerDTO) {
        try {
            return ResponseEntity.ok(authService.signUp(registerDTO));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
    @PostMapping("/signout")
    public ResponseEntity<?> signOut() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new MessageResponse("You've been signed out!"));
    }

}
