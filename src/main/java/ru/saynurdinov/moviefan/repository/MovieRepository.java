package ru.saynurdinov.moviefan.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.saynurdinov.moviefan.model.Country;
import ru.saynurdinov.moviefan.model.Genre;
import ru.saynurdinov.moviefan.model.Movie;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Integer> {

    Page<Movie> findAll(Pageable p);

    @Query("SELECT m FROM Movie m JOIN m.genres g WHERE g = :genre")
    Page<Movie> findAllByGenre(@Param("genre") Genre genre, Pageable pageable);
    @Query("SELECT m FROM Movie m JOIN m.countries c WHERE c = :country")
    Page<Movie> findAllByCountry(@Param("country")Country country, Pageable pageable);
    @Query("SELECT m FROM Movie m JOIN m.genres g JOIN m.countries c WHERE c = :country AND g = :genre")
    Page<Movie> findAllByGenreAndCountry(@Param("country")Country country, @Param("genre") Genre genre, Pageable pageable);
    @Query("SELECT m FROM Movie m WHERE m.yearOfRelease IN :years")
    Page<Movie> findAllByYearOfReleases(@Param("years") List<Integer> yearOfRelease, Pageable pageable);

    Page<Movie> findAllByYearOfReleaseLessThan(int year, Pageable pageable);

}
