package ru.saynurdinov.moviefan.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActorDTO {

    private long id;
    private String fullName;
    private String photoUrl;
}