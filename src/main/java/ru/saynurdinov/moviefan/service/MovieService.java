package ru.saynurdinov.moviefan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.saynurdinov.moviefan.model.Movie;
import ru.saynurdinov.moviefan.repository.MovieRepository;

import java.util.Optional;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Transactional(readOnly = true)
    public Optional<Movie> get(int id) {
        return movieRepository.findById(id);
    }
}
