package ru.saynurdinov.moviefan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.saynurdinov.moviefan.model.Director;


public interface DirectorRepository extends JpaRepository<Director, Long> {

}
