package ru.saynurdinov.moviefan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.saynurdinov.moviefan.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
}
