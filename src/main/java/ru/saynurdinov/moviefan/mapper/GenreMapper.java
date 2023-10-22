package ru.saynurdinov.moviefan.mapper;

import org.mapstruct.Mapper;
import ru.saynurdinov.moviefan.DTO.GenreDTO;
import ru.saynurdinov.moviefan.model.Genre;

@Mapper(componentModel = "spring")
public  interface GenreMapper {
    GenreDTO toDTO(Genre genre);
}
