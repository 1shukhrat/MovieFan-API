package ru.saynurdinov.moviefan.mapper;

import lombok.Data;
import org.mapstruct.Mapper;
import ru.saynurdinov.moviefan.DTO.ReviewDTO;
import ru.saynurdinov.moviefan.model.Review;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface ReviewMapper {
    ReviewDTO toDTO(Review review);
}
