package ru.saynurdinov.moviefan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.saynurdinov.moviefan.DTO.*;
import ru.saynurdinov.moviefan.service.CollectionService;

import java.util.List;

@RestController
@RequestMapping("/api/v2/collections")
public class CollectionController {

    private final CollectionService collectionService;

    @Autowired
    public CollectionController(CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    @GetMapping
    public List<CollectionDTO> getAllByUserId(@RequestParam("userId") long userId) {
        return collectionService.getAllByUserId(userId);
    }


    @GetMapping("/free")
    public List<CollectionPreviewDTO> getAllByMovieIdNot(@RequestParam("userId") long userId,
                                                  @RequestParam("movieId") long movieId) {
        return collectionService.getAllByMovieIdNot(userId, movieId);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody CollectionPostDTO collectionPostDTO) {
        collectionService.createCollection(collectionPostDTO);
        return ResponseEntity.ok(new MessageResponse("Коллекция успешно создана"));
    }

    @PatchMapping("/{id}/edit")
    public ResponseEntity edit(@PathVariable("id") long collectionId,
                               @RequestBody CollectionEditDTO collectionEditDTO) {
        collectionService.editCollection(collectionEditDTO, collectionId);
        return ResponseEntity.ok(new MessageResponse("Данные коллекции успешно обновлены"));
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable("id") long collectionId) {
        collectionService.removeCollection(collectionId);
        return ResponseEntity.ok(new MessageResponse("Коллекция успешно удалена"));
    }

    @PatchMapping("/{id}/addMovie")
    public ResponseEntity addMovie(@PathVariable("id") long collectionId,
                                   @RequestParam("movieId") long movieId) {
        collectionService.addMovieToCollection(movieId, collectionId);
        return ResponseEntity.ok(new MessageResponse("Фильм успешно добавлен в коллекцию"));
    }

    @PatchMapping("/{id}/removeMovie")
    public ResponseEntity removeMovie(@PathVariable("id") long collectionId,
                                   @RequestParam("movieId") long movieId) {
        collectionService.removeMovieFromCollection(movieId, collectionId);
        return ResponseEntity.ok(new MessageResponse("Фильм успешно удален из коллекции"));
    }
}
