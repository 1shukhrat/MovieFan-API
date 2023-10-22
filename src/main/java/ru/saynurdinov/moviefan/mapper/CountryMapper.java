package ru.saynurdinov.moviefan.mapper;

import org.mapstruct.Mapper;
import ru.saynurdinov.moviefan.DTO.CountryDTO;
import ru.saynurdinov.moviefan.model.Country;

@Mapper(componentModel = "spring")
public  interface CountryMapper {
    CountryDTO toDTO(Country country);
}
