package ru.saynurdinov.moviefan.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.saynurdinov.moviefan.DTO.ActorDTO;
import ru.saynurdinov.moviefan.DTO.DirectorDTO;
import ru.saynurdinov.moviefan.DTO.PreviewMovieDTO;
import ru.saynurdinov.moviefan.mapper.ActorMapper;
import ru.saynurdinov.moviefan.mapper.DirectorMapper;
import ru.saynurdinov.moviefan.mapper.PreviewMovieListMapper;
import ru.saynurdinov.moviefan.model.Actor;
import ru.saynurdinov.moviefan.model.Director;
import ru.saynurdinov.moviefan.model.Movie;
import ru.saynurdinov.moviefan.service.ActorService;
import ru.saynurdinov.moviefan.service.DirectorService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/staff")
public class StaffController {

    private final DirectorService directorService;
    private final ActorService actorService;
    private final PreviewMovieListMapper movieListMapper;
    private final ActorMapper actorMapper;
    private final DirectorMapper directorMapper;

    @Autowired
    public StaffController(DirectorService directorService, ActorService actorService, PreviewMovieListMapper movieListMapper, ActorMapper actorMapper, DirectorMapper directorMapper) {
        this.directorService = directorService;
        this.actorService = actorService;
        this.movieListMapper = movieListMapper;
        this.actorMapper = actorMapper;
        this.directorMapper = directorMapper;
    }

    @GetMapping("/directors/{id}")
    public DirectorDTO getDirectorById(@PathVariable("id") long id) {
        Director director = directorService.getById(id);
        return directorMapper.toDTO(director);
    }

    @GetMapping("/actors/{id}")
    public ActorDTO getActorById(@PathVariable("id") long id) {
        Actor actor = actorService.getById(id);
        return actorMapper.toDTO(actor);
    }


    @GetMapping("/directors/{id}/movies")
    public List<PreviewMovieDTO> getMovieListByDirectorId(@PathVariable("id") long directorId,
                                                          @RequestParam(value = "page", defaultValue = "0") int page) {
        List<Movie> movieList = directorService.getMoviesById(directorId, page);
        return movieListMapper.toDTO(movieList);
    }

    @GetMapping("/actors/{id}/movies")
    public List<PreviewMovieDTO> getMovieListByActorId(@PathVariable("id") long actorId,
                                                       @RequestParam(value = "page", defaultValue = "0")
                                                       int page) {
        List<Movie> movieList = actorService.getMoviesById(actorId, page);
        return movieListMapper.toDTO(movieList);
    }
}
