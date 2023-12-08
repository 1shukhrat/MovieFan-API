package ru.saynurdinov.moviefan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.saynurdinov.moviefan.DTO.AuthResponse;
import ru.saynurdinov.moviefan.DTO.LoginDTO;
import ru.saynurdinov.moviefan.DTO.RegisterDTO;
import ru.saynurdinov.moviefan.DTO.MessageResponse;
import ru.saynurdinov.moviefan.model.User;
import ru.saynurdinov.moviefan.repository.UserRepository;
import ru.saynurdinov.moviefan.security.JwtUtils;
import ru.saynurdinov.moviefan.security.UserDetailsImpl;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public AuthService(AuthenticationManager authenticationManager, JwtUtils jwtUtils, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public AuthResponse signIn(LoginDTO loginDTO) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken
                        (loginDTO.getLogin(),loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        ResponseCookie jwtCookies = jwtUtils.generateJwtCookie(userDetails);
        return new AuthResponse(userDetails.getId(), userDetails.getUsername(), jwtCookies.toString());
    }

    @Transactional
    public MessageResponse signUp(RegisterDTO registerDTO) {
        if (userRepository.existsUserByLogin(registerDTO.getLogin())) {
            throw new IllegalArgumentException("Error: User with this username is already exists");
        }
        User user = new User();
        user.setLogin(registerDTO.getLogin());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        userRepository.save(user);
        return new MessageResponse("The user has been successfully registered");
    }
}
