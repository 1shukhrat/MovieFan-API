package ru.saynurdinov.moviefan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.saynurdinov.moviefan.DTO.*;
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
    public List<CollectionPreviewDTO> getAllByMovieIdNot(long movieId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) {
            UserInfoDTO userInfo = (UserInfoDTO) authentication.getPrincipal();
            List<Collection> collections = collectionRepository.findAllByOwner_IdAndMovies_IdNotOrMovies_IdIsNull(userInfo.getId(), movieId);
            List<CollectionPreviewDTO> collectionPreviewDTOS = new ArrayList<>();
            collections.forEach((collection) -> {
                CollectionPreviewDTO collectionPreviewDTO =
                        new CollectionPreviewDTO(collection.getId(), collection.getName());
                collectionPreviewDTOS.add(collectionPreviewDTO);
            });
            return collectionPreviewDTOS;
        } else throw new UsernameNotFoundException("Нет доступа. Неверные данные пользователя");
    }

    @Transactional(readOnly = true)
    public List<CollectionDTO> getAllByUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) {
            UserInfoDTO userInfo = (UserInfoDTO) authentication.getPrincipal();
            List<Collection> collections = collectionRepository.findAllByOwner_Id(userInfo.getId());
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
        } else throw new UsernameNotFoundException("Нет доступа. Неверные данные пользователя");
    }

    @Transactional
    public void addMovieToCollection(long movieId, long collectionId) {
        Optional<Movie> movieOptional = movieRepository.findById(movieId);
        Optional<Collection> collectionOptional = collectionRepository.findById(collectionId);
        if (movieOptional.isPresent() && collectionOptional.isPresent()) {
            Movie movie = movieOptional.get();
            Collection collection = collectionOptional.get();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication.isAuthenticated()) {
                UserInfoDTO userInfo = (UserInfoDTO) authentication.getPrincipal();
                if (userInfo.getId() == collection.getOwner().getId()) {
                    collection.getMovies().add(movie);
                    movie.getCollections().add(collection);
                    collectionRepository.save(collection);
                    movieRepository.save(movie);
                } else throw new UsernameNotFoundException("Нет доступа. Неверные данные пользователя");
            }
        }
    }

    @Transactional
    public void removeMovieFromCollection(long movieId, long collectionId) {
        Optional<Movie> movieOptional = movieRepository.findById(movieId);
        Optional<Collection> collectionOptional = collectionRepository.findById(collectionId);
        if (movieOptional.isPresent() && collectionOptional.isPresent()) {
            Movie movie = movieOptional.get();
            Collection collection = collectionOptional.get();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication.isAuthenticated()) {
                UserInfoDTO userInfo = (UserInfoDTO) authentication.getPrincipal();
                if (userInfo.getId() == collection.getOwner().getId()) {
                    collection.getMovies().remove(movie);
                    movie.getCollections().remove(collection);
                    collectionRepository.save(collection);
                    movieRepository.save(movie);
                } else throw new UsernameNotFoundException("Нет доступа. Неверные данные пользователя");
            }
        }
    }

    @Transactional
    public void createCollection(CollectionPostDTO collectionPostDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) {
            UserInfoDTO userInfoDTO = (UserInfoDTO) authentication.getPrincipal();
            User user = userRepository.findById(userInfoDTO.getId()).get();
            Collection collection = new Collection();
            collection.setName(collectionPostDTO.getName());
            if (!collectionPostDTO.getOutline().isEmpty()) {
                collection.setOutline(collectionPostDTO.getOutline());
            }
            user.getCollections().add(collection);
            collection.setOwner(user);
            collectionRepository.save(collection);
            userRepository.save(user);
        } else throw new UsernameNotFoundException("Нет доступа. Неверные данные пользователя");
    }

    @Transactional
    public void editCollection(CollectionEditDTO collectionEditDTO, long collectionId) {
        Optional<Collection> optionalCollection = collectionRepository.findById(collectionId);
        if (optionalCollection.isPresent()) {
            Collection collection = optionalCollection.get();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication.isAuthenticated()) {
                UserInfoDTO userInfoDTO = (UserInfoDTO) authentication.getPrincipal();
                if (collection.getOwner().getId() == userInfoDTO.getId()) {
                    collection.setName(collectionEditDTO.getName());
                    collection.setOutline(collectionEditDTO.getOutline());
                    collectionRepository.save(collection);
                } else throw new UsernameNotFoundException("Нет доступа. Неверные данные пользователя");
            }
        }
    }

    @Transactional
    public void removeCollection(long collectionId) {
        Optional<Collection> collectionOptional = collectionRepository.findById(collectionId);
        if (collectionOptional.isPresent()) {
            Collection collection = collectionOptional.get();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication.isAuthenticated()) {
                UserInfoDTO userInfoDTO = (UserInfoDTO) authentication.getPrincipal();
                if (collection.getOwner().getId() == userInfoDTO.getId()) {
                    User owner = collection.getOwner();
                    owner.getCollections().remove(collection);
                    collection.getMovies().forEach(movie -> movie.getCollections().remove(collection));
                    collectionRepository.delete(collection);
                    userRepository.save(owner);
                } else throw new UsernameNotFoundException("Нет доступа. Неверные данные пользователя");
            }
        }
    }
}
