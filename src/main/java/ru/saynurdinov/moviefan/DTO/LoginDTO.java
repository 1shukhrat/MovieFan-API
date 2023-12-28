package ru.saynurdinov.moviefan.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {
    @NotBlank
    private String login;
    @NotBlank
    private String password;
}
