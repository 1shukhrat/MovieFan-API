package ru.saynurdinov.moviefan.model;

import jakarta.persistence.*;
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
    private String title;

    @Column(name = "outline")
    private String outline;

    @Column(name = "year")
    private int yearOfRelease;

    @Column(name = "poster")
    private String posterUrl;

    @Column(name = "user_rating")
    private double userRating;

    @Column(name = "rated_count")
    private int ratedCount;

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

    @ManyToMany(mappedBy = "movies")
    private List<Collection> collections;

}
