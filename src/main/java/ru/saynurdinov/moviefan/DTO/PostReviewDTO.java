package ru.saynurdinov.moviefan.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostReviewDTO {
    @NotBlank
    private String text;
    private long movieId;
}
