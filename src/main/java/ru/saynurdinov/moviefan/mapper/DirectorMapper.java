package ru.saynurdinov.moviefan.mapper;

import org.mapstruct.Mapper;
import ru.saynurdinov.moviefan.DTO.DirectorDTO;
import ru.saynurdinov.moviefan.model.Director;

@Mapper(componentModel = "spring")
public interface DirectorMapper {
    DirectorDTO toDTO(Director director);
}
