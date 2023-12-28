package ru.saynurdinov.moviefan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.saynurdinov.moviefan.DTO.PostReviewDTO;
import ru.saynurdinov.moviefan.DTO.ReviewDTO;
import ru.saynurdinov.moviefan.DTO.UserInfoDTO;
import ru.saynurdinov.moviefan.model.Movie;
import ru.saynurdinov.moviefan.model.Review;
import ru.saynurdinov.moviefan.model.User;
import ru.saynurdinov.moviefan.repository.MovieRepository;
import ru.saynurdinov.moviefan.repository.ReviewRepository;
import ru.saynurdinov.moviefan.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, UserRepository userRepository, MovieRepository movieRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
    }

    @Transactional
    public List<ReviewDTO> getAllByMovie(long movieId, int page) {
        List<Review> reviewList =  reviewRepository.getReviewByMovie_Id(movieId, PageRequest.of(page, 20)).getContent();
        List<ReviewDTO> reviewDTOList = new ArrayList<>();
        for (Review review: reviewList) {
            ReviewDTO reviewDTO = new ReviewDTO(review.getId(), review.getText(), new UserInfoDTO(review.getOwner().getId(), review.getOwner().getLogin()));
            reviewDTOList.add(reviewDTO);
        }
        return reviewDTOList;
    }

    @Transactional
    public void addReview(PostReviewDTO reviewDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) {
            UserInfoDTO userInfo = (UserInfoDTO) authentication.getPrincipal();
            User user = userRepository.findById(userInfo.getId()).get();
            Review review = new Review();
            review.setText(reviewDTO.getText());
            review.setOwner(user);
            Movie movie = movieRepository.findById(reviewDTO.getMovieId()).get();
            review.setMovie(movie);
            movie.getReviews().add(review);
            movieRepository.save(movie);
            reviewRepository.save(review);
            userRepository.save(user);
        }
        else throw new UsernameNotFoundException("Нет доступа. Данные пользователя неверны");
    }

    @Transactional
    public void remove(long id) {
        Optional<Review> reviewOptional = reviewRepository.findById(id);
        if (reviewOptional.isPresent()) {
            Review review = reviewOptional.get();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication.isAuthenticated()) {
                UserInfoDTO userInfoDTO = (UserInfoDTO) authentication.getPrincipal();
                if (userInfoDTO.getId() == review.getOwner().getId()) {
                    review.getMovie().getReviews().remove(review);
                    review.getOwner().getReviews().remove(review);
                    reviewRepository.delete(review);
                }
                else throw new UsernameNotFoundException("Нет доступа. Данные пользователя неверны");
            }

        }
    }
}
