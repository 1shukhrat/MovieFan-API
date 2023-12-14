package ru.saynurdinov.moviefan.mapper;

import org.mapstruct.Mapper;
import ru.saynurdinov.moviefan.DTO.UserInfoDTO;
import ru.saynurdinov.moviefan.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserInfoDTO toDTO(User user);
}
