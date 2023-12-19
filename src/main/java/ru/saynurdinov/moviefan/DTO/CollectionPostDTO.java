package ru.saynurdinov.moviefan.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CollectionPostDTO {

    private long userId;
    @NotBlank
    private String name;
    private String outline;

}
