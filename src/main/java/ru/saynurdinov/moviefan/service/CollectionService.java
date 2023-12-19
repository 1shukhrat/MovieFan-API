package ru.saynurdinov.moviefan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.saynurdinov.moviefan.DTO.CollectionDTO;
import ru.saynurdinov.moviefan.DTO.CollectionEditDTO;
import ru.saynurdinov.moviefan.DTO.CollectionPostDTO;
import ru.saynurdinov.moviefan.DTO.CollectionPreviewDTO;
import ru.saynurdinov.moviefan.mapper.PreviewMovieListMapper;
import ru.saynurdinov.moviefan.model.Collection;
import ru.saynurdinov.moviefan.model.Movie;
import ru.saynurdinov.moviefan.model.User;
import ru.saynurdinov.moviefan.repository.CollectionRepository;
import ru.saynurdinov.moviefan.repository.MovieRepository;
import ru.saynurdinov.moviefan.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CollectionService {

    private final CollectionRepository collectionRepository;
    private final PreviewMovieListMapper movieListMapper;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;
    @Autowired
    public CollectionService(CollectionRepository collectionRepository, PreviewMovieListMapper movieListMapper, MovieRepository movieRepository, UserRepository userRepository) {
        this.collectionRepository = collectionRepository;
        this.movieListMapper = movieListMapper;
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<CollectionPreviewDTO> getAllByMovieIdNot(long userId, long movieId) {
        List<Collection> collections = collectionRepository.findAllByOwner_IdAndMovies_IdNotOrMovies_IdIsNull(userId, movieId);
        List<CollectionPreviewDTO> collectionPreviewDTOS = new ArrayList<>();
        collections.forEach((collection) -> {
            CollectionPreviewDTO collectionPreviewDTO =
                    new CollectionPreviewDTO(collection.getId(), collection.getName());
            collectionPreviewDTOS.add(collectionPreviewDTO);
        });
        return collectionPreviewDTOS;
    }

    @Transactional(readOnly = true)
    public List<CollectionDTO> getAllByUserId(long userId) {
        List<Collection> collections = collectionRepository.findAllByOwner_Id(userId);
        List<CollectionDTO> collectionDTOS = new ArrayList<>();
        collections.forEach((collection -> {
            CollectionDTO collectionDTO = new CollectionDTO();
            collectionDTO.setId(collection.getId());
            collectionDTO.setName(collection.getName());
            collectionDTO.setOutline(collection.getOutline());
            collectionDTO.setMoviesCollection(movieListMapper.toDTO(collection.getMovies()));
            collectionDTOS.add(collectionDTO);
        }));
        return collectionDTOS;
    }

    @Transactional
    public void addMovieToCollection(long movieId, long collectionId) {
        Optional<Movie> movieOptional = movieRepository.findById(movieId);
        Optional<Collection> collectionOptional = collectionRepository.findById(collectionId);
        if (movieOptional.isPresent() && collectionOptional.isPresent()) {
            Movie movie = movieOptional.get();
            Collection collection = collectionOptional.get();
            collection.getMovies().add(movie);
            movie.getCollections().add(collection);
            collectionRepository.save(collection);
            movieRepository.save(movie);
        }
    }

    @Transactional
    public void removeMovieFromCollection(long movieId, long collectionId) {
        Optional<Movie> movieOptional = movieRepository.findById(movieId);
        Optional<Collection> collectionOptional = collectionRepository.findById(collectionId);
        if (movieOptional.isPresent() && collectionOptional.isPresent()) {
            Movie movie = movieOptional.get();
            Collection collection = collectionOptional.get();
            collection.getMovies().remove(movie);
            movie.getCollections().remove(collection);
            collectionRepository.save(collection);
            movieRepository.save(movie);
        }
    }

    @Transactional
    public void createCollection(CollectionPostDTO collectionPostDTO) {
        Optional<User> userOptional = userRepository.findById(collectionPostDTO.getUserId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Collection collection = new Collection();
            collection.setName(collectionPostDTO.getName());
            if (!collectionPostDTO.getOutline().isEmpty()) {
                collection.setOutline(collectionPostDTO.getOutline());
            }
            user.getCollections().add(collection);
            collection.setOwner(user);
            collectionRepository.save(collection);
            userRepository.save(user);
        }
    }

    @Transactional
    public void editCollection(CollectionEditDTO collectionEditDTO, long collectionId) {
        Optional<Collection> optionalCollection = collectionRepository.findById(collectionId);
        if (optionalCollection.isPresent()) {
            Collection collection = optionalCollection.get();
            collection.setName(collectionEditDTO.getName());
            collection.setOutline(collectionEditDTO.getOutline());
            collectionRepository.save(collection);
        }
    }

    @Transactional
    public void removeCollection(long collectionId) {
        Optional<Collection> collectionOptional = collectionRepository.findById(collectionId);
        if (collectionOptional.isPresent()) {
            Collection collection = collectionOptional.get();
            User owner = collection.getOwner();
            owner.getCollections().remove(collection);
            collection.getMovies().forEach(movie -> movie.getCollections().remove(collection));
            collectionRepository.delete(collection);
            userRepository.save(owner);
        }
    }
}
