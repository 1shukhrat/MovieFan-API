package ru.saynurdinov.moviefan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import ru.saynurdinov.moviefan.util.MessageResponse;
import ru.saynurdinov.moviefan.DTO.PostReviewDTO;
import ru.saynurdinov.moviefan.DTO.ReviewDTO;
import ru.saynurdinov.moviefan.service.ReviewService;

import java.util.List;

@RestController
@RequestMapping("/api/v2/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public List<ReviewDTO> getAllByMovieId(@RequestParam("movieId") long movieId,
                                           @RequestParam(value = "page", defaultValue = "0") int page) {
        return reviewService.getAllByMovie(movieId, page);
    }

    @PostMapping(value = "/add")
    public ResponseEntity<?> addReview(@RequestBody PostReviewDTO postReviewDTO) {
        try {
            reviewService.addReview(postReviewDTO);
            return ResponseEntity.ok(new MessageResponse("Отзыв был успешно опубликован"));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse(e.getMessage()));
        }

    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteReview(@PathVariable("id") long reviewId) {
        try {
            reviewService.remove(reviewId);
            return ResponseEntity.ok(new MessageResponse("Отзыв был успешно удален"));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse(e.getMessage()));
        }
    }

}
