package ru.saynurdinov.moviefan.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.saynurdinov.moviefan.DTO.MovieDTO;
import ru.saynurdinov.moviefan.DTO.MovieItemDTO;
import ru.saynurdinov.moviefan.model.Movie;
import ru.saynurdinov.moviefan.service.MovieService;
import ru.saynurdinov.moviefan.util.MovieDoesntFoundException;
import ru.saynurdinov.moviefan.util.MovieErrorResponse;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class MovieController {

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/movies")
    public List<MovieItemDTO> getMovies(@RequestParam(name = "page") int page) {
        List<Movie> movies =  movieService.getAll(page);
        return convertToDto(movies);
    }

    @GetMapping("/movies/{id}")
    public MovieDTO getMovieById(@PathVariable("id") int id) {
        return convertToDTO(movieService.get(id));
    }

    @ExceptionHandler
    public ResponseEntity<MovieErrorResponse> handleException(MovieDoesntFoundException e) {
        MovieErrorResponse response = new MovieErrorResponse("Movie with this id doesn't found", new Date());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    private List<MovieItemDTO> convertToDto(List<Movie> movies) {
        return movies.stream().map(movie -> new MovieItemDTO(movie.getId(), movie.getTitle(),
                movie.getPosterUrl(), movie.getUserRating())).toList();
    }

    private MovieDTO convertToDTO(Movie movie) {
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setId(movie.getId());
        movieDTO.setTitle(movie.getTitle());
        movieDTO.setYear(movie.getYear());
        movieDTO.setOutline(movie.getOutline());
        movieDTO.setPosterUrl(movie.getPosterUrl());
        movieDTO.setDirectors(movie.getDirectors());
        movieDTO.setCountries(movie.getCountries());
        movieDTO.setGenres(movie.getGenres());
        return movieDTO;
    }


}
