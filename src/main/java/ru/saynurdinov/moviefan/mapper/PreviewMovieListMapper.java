package ru.saynurdinov.moviefan.mapper;

import org.mapstruct.Mapper;
import ru.saynurdinov.moviefan.DTO.PreviewMovieDTO;
import ru.saynurdinov.moviefan.model.Movie;

import java.util.List;

@Mapper(componentModel = "spring", uses = {PreviewMovieMapper.class})
public interface PreviewMovieListMapper {
    List<PreviewMovieDTO> toDTO(List<Movie> movies);
}
