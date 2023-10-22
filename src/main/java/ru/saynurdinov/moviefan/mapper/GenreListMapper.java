package ru.saynurdinov.moviefan.mapper;

import org.mapstruct.Mapper;
import ru.saynurdinov.moviefan.DTO.GenreDTO;
import ru.saynurdinov.moviefan.model.Genre;

import java.util.List;

@Mapper(componentModel = "spring", uses = {GenreMapper.class})
public interface GenreListMapper {
    List<GenreDTO> toDTO(List<Genre> genres);
}
