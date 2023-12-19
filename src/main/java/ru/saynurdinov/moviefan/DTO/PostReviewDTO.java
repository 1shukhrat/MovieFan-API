package ru.saynurdinov.moviefan.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PostReviewDTO {
    @NotBlank
    private String text;
    private UserInfoDTO userInfoDTO;
    private long movieId;
}
