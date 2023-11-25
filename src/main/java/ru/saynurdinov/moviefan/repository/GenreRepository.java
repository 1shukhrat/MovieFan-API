package ru.saynurdinov.moviefan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.saynurdinov.moviefan.model.Genre;
import ru.saynurdinov.moviefan.model.Movie;

import java.util.List;

public interface GenreRepository extends JpaRepository<Genre, String> {

}
