package ru.saynurdinov.moviefan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.saynurdinov.moviefan.model.Collection;

public interface CollectionRepository extends JpaRepository<Collection, Long> {
}
