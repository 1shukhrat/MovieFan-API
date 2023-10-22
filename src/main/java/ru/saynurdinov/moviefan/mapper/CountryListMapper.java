package ru.saynurdinov.moviefan.mapper;

import org.mapstruct.Mapper;
import ru.saynurdinov.moviefan.DTO.CountryDTO;
import ru.saynurdinov.moviefan.model.Country;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CountryMapper.class})
public interface CountryListMapper {
    List<CountryDTO> toDTO (List<Country> countries);
}
