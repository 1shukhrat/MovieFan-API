package ru.saynurdinov.moviefan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import ru.saynurdinov.moviefan.util.MessageResponse;
import ru.saynurdinov.moviefan.DTO.PostRateDTO;
import ru.saynurdinov.moviefan.DTO.RatingDTO;
import ru.saynurdinov.moviefan.service.RatingService;

@RestController
@RequestMapping("/api/v2/ratings")
public class RatingController {

    private final RatingService ratingService;

    
    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @GetMapping("/get")
    public ResponseEntity<?> get(@RequestParam("movieId") long movieId) {
        try {
            RatingDTO ratingDTO = ratingService.get(movieId);
            if (ratingDTO != null) {
                return ResponseEntity.ok(ratingDTO);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse(e.getMessage()));
        }

    }

    @PostMapping("/add")
    public ResponseEntity<?> rate(@RequestBody PostRateDTO postRateDTO) {
        try {
            ratingService.rate(postRateDTO);
            return ResponseEntity.ok(new MessageResponse("Оценка добавлена"));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        try {
            ratingService.removeRating(id);
            return ResponseEntity.ok(new MessageResponse("Оценка удалена"));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse(e.getMessage()));
        }
    }
}
