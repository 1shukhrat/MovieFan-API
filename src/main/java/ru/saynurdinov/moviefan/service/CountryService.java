package ru.saynurdinov.moviefan.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.saynurdinov.moviefan.model.Country;
import ru.saynurdinov.moviefan.repository.CountryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CountryService {

    private final CountryRepository countryRepository;

    @Autowired
    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Transactional(readOnly = true)
    public List<Country> getAll() {
        return countryRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Country getByName(String name) {
        if (name == null) {
            return null;
        }
        else {
            Optional<Country> country = countryRepository.findById(name);
            return country.orElse(null);
        }
    }
}