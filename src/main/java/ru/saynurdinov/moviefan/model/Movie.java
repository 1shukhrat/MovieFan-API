package ru.saynurdinov.moviefan.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "movie")
public class Movie {

    @Id
    @Column(name = "movie_id")
    private long id;

    @Column(name = "title")
    @NotBlank
    private String title;

    @Column(name = "outline")
    @NotBlank
    private String outline;

    @Column(name = "year")
    private int yearOfRelease;

    @Column(name = "poster")
    @NotBlank
    private String posterUrl;

    @Column(name = "user_rating", precision = 3, scale = 1)
    private double userRating;

    @ManyToMany(mappedBy = "movies")
    private List<Director> directors;

    @ManyToMany(mappedBy = "movies")
    private List<Actor> actors;

    @ManyToMany(mappedBy = "movies")
    private List<Country> countries;

    @ManyToMany(mappedBy = "movies")
    private List<Genre> genres;

    @OneToMany(mappedBy = "movie")
    private List<Review> reviews;

    @OneToMany(mappedBy = "movie")
    private List<Rating> ratings;

    @ManyToMany(mappedBy = "movies")
    private List<Collection> collections;

    public void calculateRating() {
        if (ratings.isEmpty()) {
            userRating = 0.0;
        }
        else {
            userRating = (double) ratings.stream().mapToInt(Rating::getValue).sum() / ratings.size();
        }
    }

}
