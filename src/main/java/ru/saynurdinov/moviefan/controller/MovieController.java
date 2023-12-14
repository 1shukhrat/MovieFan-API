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
import ru.saynurdinov.moviefan.service.*;
import ru.saynurdinov.moviefan.util.MovieDoesntFoundException;
import ru.saynurdinov.moviefan.util.MovieErrorResponse;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/movies")
public class MovieController {

    private final DirectorService directorService;

    private final ActorService actorService;
    private final MovieService movieService;
    private final MovieMapper movieMapper;
    private final PreviewMovieListMapper movieListMapper;
    private final DirectorListMapper directorListMapper;
    private final ActorListMapper actorListMapper;
    private final GenreService genreService;
    private final CountryService countryService;


    @Autowired
    public MovieController(DirectorService directorService, ActorService actorService, MovieService movieService, MovieMapper movieMapper, PreviewMovieListMapper movieListMapper, DirectorListMapper directorListMapper, ActorListMapper actorListMapper, GenreService genreService, CountryService countryService) {
        this.directorService = directorService;
        this.actorService = actorService;
        this.movieService = movieService;
        this.movieMapper = movieMapper;
        this.movieListMapper = movieListMapper;
        this.directorListMapper = directorListMapper;
        this.actorListMapper = actorListMapper;
        this.genreService = genreService;
        this.countryService = countryService;
    }

    @GetMapping
    public List<PreviewMovieDTO> getAll(@RequestParam(name = "page", defaultValue = "0") int page,
                                        @RequestParam(required = false, name = "genre") String genreName,
                                        @RequestParam(required = false, name = "country") String countryName,
                                        @RequestParam(name = "startYear", defaultValue = "1939") int yearStart,
                                        @RequestParam(name = "endYear", defaultValue = "2023") int yearEnd) {
        List<Movie> movies = movieService.getAll(page, genreService.getByName(genreName), countryService.getByName(countryName), yearStart, yearEnd);
        return movieListMapper.toDTO(movies);
    }

    @GetMapping("/search")
    public List<PreviewMovieDTO> searchByTitle(@RequestParam(name = "title") String title, @RequestParam(name = "page") int page) {
        List<Movie> movies = movieService.getAllByTitle(page, title);
        return movieListMapper.toDTO(movies);
    }

    @GetMapping("/{id}")
    public MovieDTO getById(@PathVariable("id") long id) {
        Movie movie = movieService.get(id);
        return movieMapper.toDTO(movie);
    }


    @GetMapping("/{id}/staff")
    public StaffDTO getStaffByMovieId(@PathVariable("id") long id) {
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
