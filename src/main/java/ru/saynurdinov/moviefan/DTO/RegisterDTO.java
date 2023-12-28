package ru.saynurdinov.moviefan.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO {
    @NotBlank
    private String login;
    @NotBlank
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@()$!%*#?&])[A-Za-z\\d@()$!%*#?&]{8,20}$")
    private String password;
}
