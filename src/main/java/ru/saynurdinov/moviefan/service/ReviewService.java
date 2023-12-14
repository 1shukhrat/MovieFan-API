package ru.saynurdinov.moviefan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

import java.util.List;

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
    public List<Review> getAllByMovie(long movieId, int page) {
        return reviewRepository.getReviewByMovie_Id(movieId, PageRequest.of(page, 20)).getContent();
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
    }
}
