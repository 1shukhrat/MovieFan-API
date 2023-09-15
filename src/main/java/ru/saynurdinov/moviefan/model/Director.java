package ru.saynurdinov.moviefan.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "director")
public class Director {

    @Id
    @Column(name = "director_id")
    private long id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "photo")
    private String photoUrl;

    @ManyToMany
    @JoinTable(name = "movie_director",
    joinColumns = @JoinColumn(name = "director_id"),
    inverseJoinColumns = @JoinColumn(name = "movie_id"))
    private List<Movie> movies;

}
