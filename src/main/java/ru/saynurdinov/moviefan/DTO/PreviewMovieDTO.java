package ru.saynurdinov.moviefan.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PreviewMovieDTO {

    private long id;
    private String title;
    private String posterUrl;

}
