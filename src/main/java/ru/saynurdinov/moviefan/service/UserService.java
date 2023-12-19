package ru.saynurdinov.moviefan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.saynurdinov.moviefan.DTO.UserInfoDTO;
import ru.saynurdinov.moviefan.model.User;
import ru.saynurdinov.moviefan.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getById(long id) {
        return userRepository.findById(id).orElseThrow(() ->new IllegalArgumentException("Пользователь не найден"));
    }

    @Transactional
    public void removeAccount(long userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) {
            UserInfoDTO userInfo = (UserInfoDTO) authentication.getPrincipal();
            if (userInfo.getId() == userId) {
                userRepository.deleteById(userId);
            }
        }
    }
}
