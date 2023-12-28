package ru.saynurdinov.moviefan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.saynurdinov.moviefan.DTO.GenreDTO;
import ru.saynurdinov.moviefan.mapper.GenreListMapper;
import ru.saynurdinov.moviefan.model.Genre;
import ru.saynurdinov.moviefan.service.GenreService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/genres")
public class GenreController {


    private final GenreService genreService;

    private final GenreListMapper genreListMapper;

    @Autowired
    public GenreController(GenreService genreService, GenreListMapper genreListMapper) {
        this.genreService = genreService;
        this.genreListMapper = genreListMapper;
    }

    @GetMapping
    public List<GenreDTO> getAll() {
        List<Genre> genres = genreService.getAll();
        return genreListMapper.toDTO(genres);
    }

}
