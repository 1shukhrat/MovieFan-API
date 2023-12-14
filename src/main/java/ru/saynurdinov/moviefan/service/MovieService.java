package ru.saynurdinov.moviefan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.saynurdinov.moviefan.model.*;
import ru.saynurdinov.moviefan.repository.MovieRepository;
import ru.saynurdinov.moviefan.util.MovieDoesntFoundException;

import java.util.ArrayList;
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
    public Movie get(long id) {
        Optional<Movie> movie = movieRepository.findById(id);
        return movie.orElseThrow(MovieDoesntFoundException::new);
    }

    @Transactional(readOnly = true)
    public List<Movie> getAll(int page, Genre genre, Country country, int yearStart, int yearEnd) {
        Pageable p = PageRequest.of(page, 20);
        if (genre != null && country != null) {
            return movieRepository.findAllByCountriesAndGenresAndYearOfReleaseBetween(country, genre, yearStart, yearEnd, p).getContent();
        } else if (genre != null) {
            return movieRepository.findAllByGenresAndYearOfReleaseBetween(genre, yearStart, yearEnd, p).getContent();
        } else if (country != null) {
            return movieRepository.findAllByCountriesAndYearOfReleaseBetween(country, yearStart, yearEnd, p).getContent();
        } else {
            return movieRepository.findAllByYearOfReleaseBetween(yearStart, yearEnd, p).getContent();
        }
    }

    @Transactional(readOnly = true)
    public List<Movie> getAllByTitle(int page, String title) {
        return movieRepository.findAllByTitleContainingIgnoreCase(title, PageRequest.of(page, 20))
                .getContent();
    }
}
