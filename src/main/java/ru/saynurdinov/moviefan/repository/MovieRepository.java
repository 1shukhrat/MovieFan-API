package ru.saynurdinov.moviefan.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.saynurdinov.moviefan.model.Movie;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Integer> {

    Page<Movie> findAll(Pageable p);
}
