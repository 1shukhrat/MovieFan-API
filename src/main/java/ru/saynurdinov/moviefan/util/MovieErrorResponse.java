package ru.saynurdinov.moviefan.util;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class MovieErrorResponse {
    private String message;
    private Date date;
}
