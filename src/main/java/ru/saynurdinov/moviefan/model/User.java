package ru.saynurdinov.moviefan.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;

    @Column(name = "login")
    @NotBlank
    @Size(min = 4, max = 20)
    private String login;

    @Column(name = "password")
    @NotEmpty
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,20}$")
    private char[] password;

    @OneToMany(mappedBy = "owner")
    private List<Collection> collections;

    @OneToMany(mappedBy = "owner")
    private List<Review> reviews;

    @OneToMany(mappedBy = "owner")
    private List<Rating> ratings;


}
