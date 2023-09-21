package ru.saynurdinov.moviefan.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieItemDTO {

    private long id;
    private String title;
    private String poster;
    private double userRating;
}
