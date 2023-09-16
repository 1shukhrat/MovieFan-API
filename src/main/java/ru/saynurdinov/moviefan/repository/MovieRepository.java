package ru.saynurdinov.moviefan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.saynurdinov.moviefan.model.Movie;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
}
