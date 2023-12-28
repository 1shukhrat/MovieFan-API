package ru.saynurdinov.moviefan.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.saynurdinov.moviefan.model.*;


public interface MovieRepository extends JpaRepository<Movie, Long> {

    Slice<Movie> findAllByYearOfReleaseBetween(int yearStart, int yearEnd, Pageable p);
    Slice<Movie> findAllByGenresAndYearOfReleaseBetween(Genre genre, int yearStart, int yearEnd, Pageable p);
    Slice<Movie> findAllByCountriesAndYearOfReleaseBetween(Country country, int yearStart, int yearEnd, Pageable p);
    Slice<Movie> findAllByCountriesAndGenresAndYearOfReleaseBetween(Country country, Genre genre, int yearStart, int yearEnd, Pageable p);
    Slice<Movie> findAllByDirectors(Director director, Pageable p);
    Slice<Movie> findAllByActors(Actor actor, Pageable p);
    Slice<Movie> findAllByTitleContainingIgnoreCase(String title, Pageable p);
}
