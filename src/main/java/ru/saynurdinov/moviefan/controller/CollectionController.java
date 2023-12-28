package ru.saynurdinov.moviefan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import ru.saynurdinov.moviefan.DTO.*;
import ru.saynurdinov.moviefan.service.CollectionService;
import ru.saynurdinov.moviefan.util.MessageResponse;


@RestController
@RequestMapping("/api/v2/collections")
public class CollectionController {

    private final CollectionService collectionService;

    @Autowired
    public CollectionController(CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    @GetMapping
    public ResponseEntity<?> getAllByUserId() {
        try {
            return ResponseEntity.ok(collectionService.getAllByUserId());
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse(e.getMessage()));
        }
    }

    @GetMapping("/free")
    public ResponseEntity<?> getAllByMovieIdNot(@RequestParam("movieId") long movieId) {
        try {
            return ResponseEntity.ok(collectionService.getAllByMovieIdNot(movieId));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse(e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CollectionPostDTO collectionPostDTO) {
        try {
            collectionService.createCollection(collectionPostDTO);
            return ResponseEntity.ok(new MessageResponse("Коллекция успешно создана"));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse(e.getMessage()));
        }
    }

    @PatchMapping("/{id}/edit")
    public ResponseEntity<?> edit(@PathVariable("id") long collectionId,
                               @RequestBody CollectionEditDTO collectionEditDTO) {
        try {
            collectionService.editCollection(collectionEditDTO, collectionId);
            return ResponseEntity.ok(new MessageResponse("Данные коллекции успешно обновлены"));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse(e.getMessage()));
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long collectionId) {
        try {
            collectionService.removeCollection(collectionId);
            return ResponseEntity.ok(new MessageResponse("Коллекция успешно удалена"));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse(e.getMessage()));
        }
    }

    @PatchMapping("/{id}/addMovie")
    public ResponseEntity<?> addMovie(@PathVariable("id") long collectionId,
                                   @RequestParam("movieId") long movieId) {
        try {
            collectionService.addMovieToCollection(movieId, collectionId);
            return ResponseEntity.ok(new MessageResponse("Фильм успешно добавлен в коллекцию"));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse(e.getMessage()));
        }
    }

    @PatchMapping("/{id}/removeMovie")
    public ResponseEntity<?> removeMovie(@PathVariable("id") long collectionId,
                                   @RequestParam("movieId") long movieId) {
        try {
            collectionService.removeMovieFromCollection(movieId, collectionId);
            return ResponseEntity.ok(new MessageResponse("Фильм успешно удален из коллекции"));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse(e.getMessage()));
        }
    }
}
