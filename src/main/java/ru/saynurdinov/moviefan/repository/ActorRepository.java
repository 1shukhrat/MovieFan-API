package ru.saynurdinov.moviefan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.saynurdinov.moviefan.model.Actor;

public interface ActorRepository extends JpaRepository<Actor, Integer> {
}
