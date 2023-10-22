package ru.saynurdinov.moviefan.mapper;

import org.mapstruct.Mapper;
import ru.saynurdinov.moviefan.DTO.ActorDTO;
import ru.saynurdinov.moviefan.model.Actor;

import java.util.List;

@Mapper(componentModel = "spring", uses = ActorMapper.class)
public interface ActorListMapper {
    List<ActorDTO> toDTO(List<Actor> actors);
}
