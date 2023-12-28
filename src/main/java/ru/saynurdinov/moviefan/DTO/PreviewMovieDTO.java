package ru.saynurdinov.moviefan.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PreviewMovieDTO {

    private long id;
    private String title;
    private String posterUrl;

}
