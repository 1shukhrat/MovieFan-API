package ru.saynurdinov.moviefan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.saynurdinov.moviefan.model.Genre;


public interface GenreRepository extends JpaRepository<Genre, String> {

}
