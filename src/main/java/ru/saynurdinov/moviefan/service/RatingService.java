package ru.saynurdinov.moviefan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.saynurdinov.moviefan.DTO.PostRateDTO;
import ru.saynurdinov.moviefan.DTO.RatingDTO;
import ru.saynurdinov.moviefan.model.Movie;
import ru.saynurdinov.moviefan.model.Rating;
import ru.saynurdinov.moviefan.model.User;
import ru.saynurdinov.moviefan.repository.MovieRepository;
import ru.saynurdinov.moviefan.repository.RatingRepository;
import ru.saynurdinov.moviefan.repository.UserRepository;

import java.util.Optional;

@Service
public class RatingService {

    private final MovieRepository movieRepository;
    private final UserRepository userRepository;

    private final RatingRepository ratingRepository;

    @Autowired
    public RatingService(MovieRepository movieRepository, UserRepository userRepository, RatingRepository ratingRepository) {
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
        this.ratingRepository = ratingRepository;
    }

    @Transactional
    public void rate(PostRateDTO postRateDTO) {
        Optional<Movie> movieOptional = movieRepository.findById(postRateDTO.getMovieId());
        Optional<User> optionalUser = userRepository.findById(postRateDTO.getUserId());
        if (movieOptional.isPresent() && optionalUser.isPresent()) {
            Movie movie = movieOptional.get();
            User user = optionalUser.get();
            Rating rating = new Rating();
            rating.setOwner(user);
            rating.setMovie(movie);
            rating.setValue(postRateDTO.getValue());
            user.getRatings().add(rating);
            movie.getRatings().add(rating);
            movie.calculateRating();
            ratingRepository.save(rating);
            userRepository.save(user);
        }
    }

    @Transactional(readOnly = true)
    public RatingDTO get(long movieId, long userId) {
        Optional<Rating> optionalRating = ratingRepository.findByMovie_IdAndOwner_Id(movieId, userId);
        if (optionalRating.isPresent()) {
            Rating rating = optionalRating.get();
            return new RatingDTO(rating.getId(), rating.getValue());
        }
        return null;
    }

    @Transactional
    public void removeRating(long ratingId) {
        Optional<Rating> optionalRating = ratingRepository.findById(ratingId);
        if (optionalRating.isPresent()) {
            Rating rating = optionalRating.get();
            Movie movie = rating.getMovie();
            User user = rating.getOwner();
            movie.getRatings().remove(rating);
            user.getRatings().remove(rating);
            movie.calculateRating();
            movieRepository.save(movie);
            userRepository.save(user);
            ratingRepository.delete(rating);
        }
    }
}
