package ru.saynurdinov.moviefan.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewDTO {

    private long id;
    private String text;
    private UserInfoDTO user;

}
