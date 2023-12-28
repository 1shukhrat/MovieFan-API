package ru.saynurdinov.moviefan.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "\"user\"")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;

    @Column(name = "login")
    @NotBlank
    private String login;

    @Column(name = "password")
    @NotBlank
    private String password;

    @OneToMany(mappedBy = "owner")
    private List<Collection> collections;

    @OneToMany(mappedBy = "owner")
    private List<Review> reviews;

    @OneToMany(mappedBy = "owner")
    private List<Rating> ratings;


}
