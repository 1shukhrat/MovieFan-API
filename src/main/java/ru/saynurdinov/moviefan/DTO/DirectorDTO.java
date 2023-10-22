package ru.saynurdinov.moviefan.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DirectorDTO {

    private long id;
    private String fullName;
    private String photoUrl;
}
