package ru.saynurdinov.moviefan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.saynurdinov.moviefan.model.Country;

public interface CountryRepository extends JpaRepository<Country, String> {
}
