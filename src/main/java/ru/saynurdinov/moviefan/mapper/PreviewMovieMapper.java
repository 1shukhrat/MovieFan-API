package ru.saynurdinov.moviefan.mapper;

import org.mapstruct.Mapper;
import ru.saynurdinov.moviefan.DTO.PreviewMovieDTO;
import ru.saynurdinov.moviefan.model.Movie;

@Mapper(componentModel = "spring")
public interface PreviewMovieMapper {
    PreviewMovieDTO toDTO(Movie movie);
}
