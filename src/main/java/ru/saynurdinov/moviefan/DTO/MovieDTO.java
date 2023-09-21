package ru.saynurdinov.moviefan.DTO;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.saynurdinov.moviefan.model.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDTO {

    private long id;
    private String title;
    private String outline;
    private int year;
    private String posterUrl;
    private double userRating;
    private List<DirectorDTO> directors;
    private List<CountryDTO> countries;
    private List<GenreDTO> genres;

    public void setDirectors(List<Director> directors) {
        this.directors =
                directors.stream().map(director -> new DirectorDTO(director.getId(), director.getFullName(), director.getPhotoUrl())).toList();
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres.stream().map(genre -> new GenreDTO(genre.getName())).toList();
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries.stream().map(country -> new CountryDTO(country.getName())).toList();
    }

}
