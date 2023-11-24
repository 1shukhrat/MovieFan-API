package ru.saynurdinov.moviefan.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.saynurdinov.moviefan.DTO.MovieDTO;
import ru.saynurdinov.moviefan.model.Movie;



@Mapper(componentModel = "spring", uses = {GenreListMapper.class, CountryListMapper.class})
public interface MovieMapper {
    @Mapping(source = "movie.yearOfRelease", target = "yearOfRelease")
    MovieDTO toDTO(Movie movie);
}
