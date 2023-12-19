package ru.saynurdinov.moviefan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.saynurdinov.moviefan.model.Collection;

import java.util.List;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, Long> {

    @Query("SELECT c FROM Collection c WHERE c.owner.id = :ownerId AND NOT EXISTS (SELECT mc FROM c.movies mc WHERE mc.id = :movieId)")
    List<Collection> findAllByOwner_IdAndMovies_IdNotOrMovies_IdIsNull(@Param("ownerId") Long ownerId, @Param("movieId") Long movieId);
    List<Collection> findAllByOwner_Id(Long ownerId);

}
