package ru.saynurdinov.moviefan.DTO;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CollectionDTO {
    private long id;
    private String name;
    private String outline;
    private List<PreviewMovieDTO> moviesCollection;

}
