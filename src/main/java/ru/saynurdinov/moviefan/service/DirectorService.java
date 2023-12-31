package ru.saynurdinov.moviefan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.saynurdinov.moviefan.model.Director;
import ru.saynurdinov.moviefan.model.Movie;
import ru.saynurdinov.moviefan.repository.DirectorRepository;
import ru.saynurdinov.moviefan.repository.MovieRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class DirectorService {

    private final DirectorRepository directorRepository;

    private final MovieRepository movieRepository;

    @Autowired
    public DirectorService(DirectorRepository directorRepository, MovieRepository movieRepository) {
        this.directorRepository = directorRepository;
        this.movieRepository = movieRepository;
    }

    @Transactional(readOnly = true)
    public Director getById(long id) {
        return directorRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Movie> getMoviesById(long id, int page) {
        Optional<Director> director = directorRepository.findById(id);
        return director.map(value -> movieRepository.findAllByDirectors(value, PageRequest.of(page, 20)).getContent()).orElse(Collections.emptyList());
    }
}
