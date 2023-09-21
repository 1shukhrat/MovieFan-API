package ru.saynurdinov.moviefan.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "collection")
public class Collection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "collection_id")
    private long id;

    @Column(name = "name")
    @NotBlank
    @Size(min = 5)
    private String name;

    @Column(name = "outline")
    @Size(min = 5)
    private String outline;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User owner;

    @ManyToMany
    @JoinTable(name = "movie_collection",
    joinColumns = @JoinColumn(name = "collection_id"),
    inverseJoinColumns = @JoinColumn(name = "movie_id"))
    private List<Movie> movies;

}
