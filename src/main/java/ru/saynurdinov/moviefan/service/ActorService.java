package ru.saynurdinov.moviefan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.saynurdinov.moviefan.model.Actor;
import ru.saynurdinov.moviefan.model.Movie;
import ru.saynurdinov.moviefan.repository.ActorRepository;
import ru.saynurdinov.moviefan.repository.MovieRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ActorService {

    private final ActorRepository actorRepository;
    private final MovieRepository movieRepository;

    @Autowired
    public ActorService(ActorRepository actorRepository, MovieRepository movieRepository) {
        this.actorRepository = actorRepository;
        this.movieRepository = movieRepository;
    }

    @Transactional(readOnly = true)
    public Actor getById(int id) {
        return actorRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Movie> getMoviesById(int id, int page) {
        Optional<Actor> actor = actorRepository.findById(id);
        return actor.isPresent()?movieRepository.findAllByActors(actor.get(), PageRequest.of(page, 20)).getContent():Collections.emptyList();
    }
}
