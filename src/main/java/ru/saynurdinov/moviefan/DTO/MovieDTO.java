package ru.saynurdinov.moviefan.DTO;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieDTO {

    private long id;
    private String title;
    private String posterUrl;
    private String outline;
    private int yearOfRelease;
    private double userRating;
    private List<CountryDTO> countries;
    private List<GenreDTO> genres;

}
