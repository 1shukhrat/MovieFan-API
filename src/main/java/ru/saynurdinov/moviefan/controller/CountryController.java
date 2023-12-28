package ru.saynurdinov.moviefan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.saynurdinov.moviefan.DTO.CountryDTO;
import ru.saynurdinov.moviefan.mapper.CountryListMapper;
import ru.saynurdinov.moviefan.model.Country;
import ru.saynurdinov.moviefan.service.CountryService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/countries")
public class CountryController {


    private final CountryService countryService;
    private final CountryListMapper countryListMapper;

    @Autowired
    public CountryController(CountryService countryService, CountryListMapper countryListMapper) {
        this.countryService = countryService;
        this.countryListMapper = countryListMapper;
    }


    @GetMapping
    public List<CountryDTO> getAll() {
        List<Country> countries = countryService.getAll();
        return countryListMapper.toDTO(countries);
    }

}