package ru.saynurdinov.moviefan.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.saynurdinov.moviefan.model.Genre;
import ru.saynurdinov.moviefan.repository.GenreRepository;

import java.util.List;
import java.util.Optional;

@Service
public class GenreService {

    private final GenreRepository genreRepository;

    @Autowired
    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Transactional(readOnly = true)
    public List<Genre> getAll() {
        return genreRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Genre getByName(String name) {
        if (name == null) {
            return null;
        }
        else {
            Optional<Genre> genre = genreRepository.findById(name);
            return genre.orElse(null);
        }

    }
}
