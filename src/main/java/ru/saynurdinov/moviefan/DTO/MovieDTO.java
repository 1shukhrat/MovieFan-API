package ru.saynurdinov.moviefan.DTO;

import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.saynurdinov.moviefan.model.Country;
import ru.saynurdinov.moviefan.model.Genre;

import java.util.List;

@Data
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
