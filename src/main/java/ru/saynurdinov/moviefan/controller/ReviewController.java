package ru.saynurdinov.moviefan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.saynurdinov.moviefan.DTO.PostReviewDTO;
import ru.saynurdinov.moviefan.DTO.ReviewDTO;
import ru.saynurdinov.moviefan.mapper.ReviewListMapper;
import ru.saynurdinov.moviefan.model.Review;
import ru.saynurdinov.moviefan.service.ReviewService;

import java.util.List;

@RestController
@RequestMapping("/api/v2/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewListMapper reviewListMapper;

    @Autowired
    public ReviewController(ReviewService reviewService, ReviewListMapper reviewListMapper) {
        this.reviewService = reviewService;
        this.reviewListMapper = reviewListMapper;
    }

    @GetMapping
    public List<ReviewDTO> getAllByMovieId(@RequestParam("movieId") long movieId,
                                           @RequestParam(value = "page", defaultValue = "0") int page) {
        List<Review> reviewList = reviewService.getAllByMovie(movieId, page);
        return reviewListMapper.toDTO(reviewList);
    }

    @PostMapping
    public ResponseEntity.BodyBuilder addReview(@RequestBody PostReviewDTO postReviewDTO) {
        reviewService.addReview(postReviewDTO);
        return ResponseEntity.ok();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity.BodyBuilder deleteReview(@PathVariable("id") long previewId) {
        return null;
    }

}
