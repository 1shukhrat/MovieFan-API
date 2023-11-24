package ru.saynurdinov.moviefan.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.saynurdinov.moviefan.DTO.MovieDTO;
import ru.saynurdinov.moviefan.DTO.PreviewMovieDTO;
import ru.saynurdinov.moviefan.DTO.StaffDTO;
import ru.saynurdinov.moviefan.mapper.ActorListMapper;
import ru.saynurdinov.moviefan.mapper.DirectorListMapper;
import ru.saynurdinov.moviefan.mapper.MovieMapper;
import ru.saynurdinov.moviefan.mapper.PreviewMovieListMapper;
import ru.saynurdinov.moviefan.model.Movie;
import ru.saynurdinov.moviefan.service.MovieService;
import ru.saynurdinov.moviefan.util.MovieDoesntFoundException;
import ru.saynurdinov.moviefan.util.MovieErrorResponse;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/movies")
public class MovieController {

    private final MovieService movieService;
    private final MovieMapper movieMapper;
    private final PreviewMovieListMapper movieListMapper;

    private final DirectorListMapper directorListMapper;

    private final ActorListMapper actorListMapper;


    @Autowired
    public MovieController(MovieService movieService, MovieMapper movieMapper, PreviewMovieListMapper movieListMapper, DirectorListMapper directorListMapper, ActorListMapper actorListMapper) {
        this.movieService = movieService;
        this.movieMapper = movieMapper;
        this.movieListMapper = movieListMapper;
        this.directorListMapper = directorListMapper;
        this.actorListMapper = actorListMapper;
    }

    @GetMapping
    public List<PreviewMovieDTO> getMovies(@RequestParam(name = "page") int page) {
        List<Movie> movies =  movieService.getAll(page);
        return movieListMapper.toDTO(movies);
    }

    @GetMapping("/{id}")
    public MovieDTO getMovieById(@PathVariable("id") int id) {
        Movie movie = movieService.get(id);
        return movieMapper.toDTO(movie);
    }


    @GetMapping("/{id}/staff")
    public StaffDTO getStaffByMovieId(@PathVariable("id") int id) {
        StaffDTO staff = new StaffDTO();
        Movie movie = movieService.get(id);
        staff.setMovieTitle(movie.getTitle());
        staff.setDirectors(directorListMapper.toDTO(movie.getDirectors()));
        staff.setActors(actorListMapper.toDTO(movie.getActors()));
        return staff;
    }


    @ExceptionHandler
    public ResponseEntity<MovieErrorResponse> handleException(MovieDoesntFoundException e) {
        MovieErrorResponse movieErrorResponse = new MovieErrorResponse("Фильм не найден", LocalDateTime.now());
        return new ResponseEntity<>(movieErrorResponse, HttpStatus.NOT_FOUND);
    }


}
