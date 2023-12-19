package ru.saynurdinov.moviefan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.saynurdinov.moviefan.DTO.MessageResponse;
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
    public ResponseEntity get(@RequestParam("movieId") long movieId, @RequestParam("userId") long userId) {
        RatingDTO ratingDTO = ratingService.get(movieId, userId);
        if (ratingDTO != null) {
            return ResponseEntity.ok(ratingDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity rate(@RequestBody PostRateDTO postRateDTO) {
        ratingService.rate(postRateDTO);
        return ResponseEntity.ok(new MessageResponse("Оценка добавлена"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") long id) {
        ratingService.removeRating(id);
        return ResponseEntity.ok(new MessageResponse("Оценка удалена"));
    }
}
