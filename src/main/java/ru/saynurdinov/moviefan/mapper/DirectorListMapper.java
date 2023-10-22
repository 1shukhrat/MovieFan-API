package ru.saynurdinov.moviefan.mapper;

import org.mapstruct.Mapper;
import ru.saynurdinov.moviefan.DTO.DirectorDTO;
import ru.saynurdinov.moviefan.model.Director;

import java.util.List;

@Mapper(componentModel = "spring", uses = DirectorDTO.class)
public interface DirectorListMapper {
    List<DirectorDTO> toDTO(List<Director> directors);
}
