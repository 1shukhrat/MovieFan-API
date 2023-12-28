package ru.saynurdinov.moviefan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.saynurdinov.moviefan.util.AuthResponse;
import ru.saynurdinov.moviefan.DTO.LoginDTO;
import ru.saynurdinov.moviefan.DTO.RegisterDTO;
import ru.saynurdinov.moviefan.util.MessageResponse;
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

    private final UserDetailsService userDetailsService;
    @Autowired
    public AuthService(AuthenticationManager authenticationManager, JwtUtils jwtUtils, UserRepository userRepository, PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    @Transactional
    public AuthResponse signIn(LoginDTO loginDTO) throws BadCredentialsException {
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken
                        (loginDTO.getLogin(),loginDTO.getPassword()));
        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(loginDTO.getLogin());
        String token = jwtUtils.generateToken(userDetails);
        return new AuthResponse(userDetails.getId(), userDetails.getLogin(), token);
    }

    @Transactional
    public MessageResponse signUp(RegisterDTO registerDTO) {
        if (userRepository.findByLogin(registerDTO.getLogin()).isPresent()) {
            throw new IllegalArgumentException("Error: User with this username is already exists");
        }
        User user = new User();
        user.setLogin(registerDTO.getLogin());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        userRepository.save(user);
        return new MessageResponse("The user has been successfully registered");
    }
}
