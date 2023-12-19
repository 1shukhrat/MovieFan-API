package ru.saynurdinov.moviefan.DTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostRateDTO {

    @Min(0)
    @Max(10)
    int value;
    long movieId;
    long userId;
}
