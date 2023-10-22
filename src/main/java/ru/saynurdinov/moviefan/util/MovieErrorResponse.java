package ru.saynurdinov.moviefan.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
public class MovieErrorResponse {
    private String message;
    private LocalDateTime date;

}
