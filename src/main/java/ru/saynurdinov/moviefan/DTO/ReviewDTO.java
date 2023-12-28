package ru.saynurdinov.moviefan.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {

    private long id;
    private String text;
    private UserInfoDTO user;

}
