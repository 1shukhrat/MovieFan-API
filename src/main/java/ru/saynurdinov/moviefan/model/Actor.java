package ru.saynurdinov.moviefan.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "actor")
public class Actor {

    @Id
    @Column(name = "actor_id")
    private long id;

    @Column(name = "full_name")
    @NotBlank
    private String fullName;

    @Column(name = "photo")
    private String photoUrl;

    @ManyToMany
    @JoinTable(name = "movie_actor",
    joinColumns = @JoinColumn(name = "actor_id"),
    inverseJoinColumns = @JoinColumn(name = "movie_id"))
    private List<Movie> movies;

}
