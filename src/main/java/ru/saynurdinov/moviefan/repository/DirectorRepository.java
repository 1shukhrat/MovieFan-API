package ru.saynurdinov.moviefan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.saynurdinov.moviefan.model.Actor;
import ru.saynurdinov.moviefan.model.Director;

import java.util.List;

public interface DirectorRepository extends JpaRepository<Director, Long> {

}
