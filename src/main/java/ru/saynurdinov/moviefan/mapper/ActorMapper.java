package ru.saynurdinov.moviefan.mapper;

import org.mapstruct.Mapper;
import ru.saynurdinov.moviefan.DTO.ActorDTO;
import ru.saynurdinov.moviefan.model.Actor;

@Mapper(componentModel = "spring")
public interface ActorMapper {
    ActorDTO toDTO(Actor actor);
}
