package ru.saynurdinov.moviefan.DTO;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StaffDTO {
    private String movieTitle;
    private List<DirectorDTO> directors;
    private List<ActorDTO> actors;
}
