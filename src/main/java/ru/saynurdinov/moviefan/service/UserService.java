package ru.saynurdinov.moviefan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.saynurdinov.moviefan.DTO.UserInfoDTO;
import ru.saynurdinov.moviefan.model.Movie;
import ru.saynurdinov.moviefan.model.User;
import ru.saynurdinov.moviefan.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void removeAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) {
            UserInfoDTO userInfo = (UserInfoDTO) authentication.getPrincipal();
            User user = userRepository.findById(userInfo.getId()).get();
            user.getRatings().forEach(rating -> {
                Movie movie = rating.getMovie();
                movie.getRatings().remove(rating);
                movie.calculateRating();
            });
            userRepository.deleteById(userInfo.getId());

        }
        else throw new UsernameNotFoundException("Нет доступа. Неверные данные пользователя");
    }
}
