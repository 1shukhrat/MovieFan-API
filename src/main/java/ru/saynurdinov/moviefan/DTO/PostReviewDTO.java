package ru.saynurdinov.moviefan.DTO;

import lombok.Data;

@Data
public class PostReviewDTO {
    private String text;
    private UserInfoDTO userInfoDTO;
    private long movieId;
}
