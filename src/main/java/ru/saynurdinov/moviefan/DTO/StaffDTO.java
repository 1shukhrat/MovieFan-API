package ru.saynurdinov.moviefan.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StaffDTO {
    private String movieTitle;
    private List<DirectorDTO> directors;
    private List<ActorDTO> actors;
}
