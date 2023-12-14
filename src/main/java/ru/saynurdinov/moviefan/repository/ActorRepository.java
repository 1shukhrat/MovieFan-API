package ru.saynurdinov.moviefan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.saynurdinov.moviefan.model.Actor;

import java.util.List;

public interface ActorRepository extends JpaRepository<Actor, Long> {

}
