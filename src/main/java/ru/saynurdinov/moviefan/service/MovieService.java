package ru.saynurdinov.moviefan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.saynurdinov.moviefan.model.Movie;
import ru.saynurdinov.moviefan.repository.MovieRepository;
import ru.saynurdinov.moviefan.util.MovieDoesntFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Transactional(readOnly = true)
    public Movie get(int id) {
        Optional<Movie> movie = movieRepository.findById(id);
        return movie.orElseThrow(MovieDoesntFoundException::new);
    }

    @Transactional(readOnly = true)
    public List<Movie> getAll(int page) {
        return movieRepository.findAll(PageRequest.of(page, 20)).getContent();
    }
}
