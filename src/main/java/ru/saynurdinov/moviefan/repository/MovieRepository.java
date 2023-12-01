package ru.saynurdinov.moviefan.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import ru.saynurdinov.moviefan.model.*;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Integer> {

    Slice<Movie> findAllByYearOfReleaseBetween(int yearStart, int yearEnd, Pageable p);
    Slice<Movie> findAllByGenresAndYearOfReleaseBetween(Genre genre, int yearStart, int yearEnd, Pageable p);
    Slice<Movie> findAllByCountriesAndYearOfReleaseBetween(Country country, int yearStart, int yearEnd, Pageable p);
    Slice<Movie> findAllByCountriesAndGenresAndYearOfReleaseBetween(Country country, Genre genre, int yearStart, int yearEnd, Pageable p);
    Slice<Movie> findAllByDirectors(Director director, Pageable p);
    Slice<Movie> findAllByActors(Actor actor, Pageable p);

    Slice<Movie> findAllByTitleContaining(String title, Pageable p);


}
