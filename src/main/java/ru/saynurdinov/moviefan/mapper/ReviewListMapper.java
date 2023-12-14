package ru.saynurdinov.moviefan.mapper;

import org.mapstruct.Mapper;
import ru.saynurdinov.moviefan.DTO.ReviewDTO;
import ru.saynurdinov.moviefan.model.Review;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ReviewMapper.class})
public interface ReviewListMapper {
    List<ReviewDTO> toDTO(List<Review> reviewList);
}
