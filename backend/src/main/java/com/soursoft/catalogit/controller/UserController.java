package com.soursoft.catalogit.controller;

import com.soursoft.catalogit.dto.MovieDataDTO;
import com.soursoft.catalogit.dto.MovieShortDataDTO;
import com.soursoft.catalogit.dto.WatchlistElementDTO;
import com.soursoft.catalogit.entity.Movie;
import com.soursoft.catalogit.entity.User;
import com.soursoft.catalogit.entity.WatchlistElement;
import com.soursoft.catalogit.exception.UserByIdNotFoundException;
import com.soursoft.catalogit.exception.WatchlistElementAlreadyExistsException;
import com.soursoft.catalogit.exception.WatchlistElementNotFoundException;
import com.soursoft.catalogit.service.MovieService;
import com.soursoft.catalogit.service.UserService;
import com.soursoft.catalogit.service.WatchlistService;
import com.soursoft.catalogit.valueobject.requests.user.UserWatchlistMovieRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ssl.SslProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/user")
public class UserController {

    private UserService userService;
    private MovieService movieService;
    private WatchlistService watchlistService;

    @Autowired
    public UserController(UserService userService, MovieService movieService, WatchlistService watchlistService) {
        this.userService = userService;
        this.movieService = movieService;
        this.watchlistService = watchlistService;
    }

    @GetMapping(value = "/users/{userId}/watchlist")
    public ResponseEntity<Set<WatchlistElementDTO>> getUserWatchlist(@PathVariable Long userId) {
        User user = userService.findUserById(userId);
        Set<WatchlistElement> userWatchlist = this.watchlistService.getUserWatchlist(user);
        var result = userWatchlist.stream()
                    .map(element ->  WatchlistElementDTO.from(element))
                    .collect(Collectors.toSet());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/users/{userId}/watchlist")
    public ResponseEntity<Set<WatchlistElementDTO>> addMovieToUserWatchlist(@PathVariable Long userId,
                                                                        @RequestBody UserWatchlistMovieRequest request) {
        User user = this.userService.findUserById(userId);
        Movie movie = this.movieService.findMovieById(request.movieId());
        Optional<WatchlistElement> existingElement = this.watchlistService.getWatchlistElementByUserAndMovie(user, movie);
        if(existingElement.isPresent()) {
            throw new WatchlistElementAlreadyExistsException(user, movie);
        } else {
            WatchlistElement newElement = this.watchlistService.createNewWatchlistElementForUser(user, movie);
            user = this.userService.addWatchlistElementToUser(user, newElement);
            Set<WatchlistElementDTO> userWatchlist = this.watchlistService.getUserWatchlistDTO(user);
            return new ResponseEntity(userWatchlist, HttpStatus.CREATED);
        }
    }

    @DeleteMapping(value = "/users/{userId}/watchlist")
    public ResponseEntity<WatchlistElementDTO> removeMovieFromUserWatchlist(@PathVariable Long userId,
                                                                        @RequestBody UserWatchlistMovieRequest request) {
        User user = this.userService.findUserById(userId);
        Movie movie = this.movieService.findMovieById(request.movieId());
        Optional<WatchlistElement> existingElement = this.watchlistService.getWatchlistElementByUserAndMovie(user, movie);

        if(existingElement.isPresent()) {
            WatchlistElement element = existingElement.get();
            user = this.userService.removeWatchlistElementFromUser(user, element);
            element = this.watchlistService.removeWatchlistElementFromUser(user, element);
            return new ResponseEntity(WatchlistElementDTO.from(element), HttpStatus.OK);
        } else {
            throw new WatchlistElementNotFoundException(user, movie);
        }
    }

    @GetMapping(value = "/users/{userId}/watchlist/{movieId}")
    public ResponseEntity<WatchlistElementDTO> getMovieFromUserWatchlistById(@PathVariable Long userId,
                                                                        @PathVariable Long movieId) {
        User user = this.userService.findUserById(userId);
        Movie movie = this.movieService.findMovieById(movieId);
        Optional<WatchlistElement> optionalElement = this.watchlistService.getWatchlistElementByUserAndMovie(user, movie);

        if(optionalElement.isPresent()) {
            WatchlistElement foundElement = optionalElement.get();
            return new ResponseEntity<>(WatchlistElementDTO.from(foundElement), HttpStatus.OK);
        } else {
            throw new WatchlistElementNotFoundException(user, movie);
        }
    }

    @DeleteMapping(value = "/users/{userId}/watchlist/{movieId}")
    public ResponseEntity<MovieDataDTO> removeMovieFromUserWatchlistById(@PathVariable Long userId,
                                                                     @PathVariable Long movieId) {
        User user = this.userService.findUserById(userId);
        Movie movie = this.movieService.findMovieById(movieId);
        Optional<WatchlistElement> existingElement = this.watchlistService.getWatchlistElementByUserAndMovie(user, movie);

        if(existingElement.isPresent()) {
            WatchlistElement element = existingElement.get();
            element = this.watchlistService.removeWatchlistElementFromUser(user, element);
            return new ResponseEntity(WatchlistElementDTO.from(element), HttpStatus.OK);
        } else {
            throw new WatchlistElementNotFoundException(user, movie);
        }
    }



}
