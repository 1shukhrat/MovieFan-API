package ru.saynurdinov.moviefan.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.saynurdinov.moviefan.DTO.PreviewMovieDTO;
import ru.saynurdinov.moviefan.mapper.PreviewMovieListMapper;
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

    @Autowired
    public StaffController(DirectorService directorService, ActorService actorService, PreviewMovieListMapper movieListMapper) {
        this.directorService = directorService;
        this.actorService = actorService;
        this.movieListMapper = movieListMapper;
    }

    @GetMapping("/directors/{id}/movies")
    public List<PreviewMovieDTO> getMovieListByDirectorId(@PathVariable("id") int directorId,
                                                          @RequestParam(value = "page", defaultValue = "0") int page) {
        List<Movie> movieList = directorService.getMoviesById(directorId, page);
        return movieListMapper.toDTO(movieList);
    }

    @GetMapping("/actors/{id}/movies")
    public List<PreviewMovieDTO> getMovieListByActorId(@PathVariable("id") int actorId,
                                                       @RequestParam(value = "page", defaultValue = "0")
                                                       int page) {
        List<Movie> movieList = actorService.getMoviesById(actorId, page);
        return movieListMapper.toDTO(movieList);
    }
}
