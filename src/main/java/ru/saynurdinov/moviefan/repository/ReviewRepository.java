package ru.saynurdinov.moviefan.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.saynurdinov.moviefan.model.Review;


@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Slice<Review> getReviewByMovie_Id(Long movieId, Pageable pageable);
}
