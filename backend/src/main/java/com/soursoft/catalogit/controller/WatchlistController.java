package com.soursoft.catalogit.controller;

import com.soursoft.catalogit.dto.ReviewDTO;
import com.soursoft.catalogit.dto.WatchlistElementDTO;
import com.soursoft.catalogit.entity.Movie;
import com.soursoft.catalogit.entity.Review;
import com.soursoft.catalogit.entity.User;
import com.soursoft.catalogit.entity.WatchlistElement;
import com.soursoft.catalogit.exception.WatchlistElementNotFoundException;
import com.soursoft.catalogit.service.MovieService;
import com.soursoft.catalogit.service.ReviewService;
import com.soursoft.catalogit.service.UserService;
import com.soursoft.catalogit.service.WatchlistService;
import com.soursoft.catalogit.valueobject.requests.ReviewPostRequest;
import com.soursoft.catalogit.valueobject.response.MovieStatisticsResponse;
import com.soursoft.catalogit.valueobject.response.WatchlistElementAverageRatingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(value = "http://localhost:4200")
@RequestMapping(value = "/api/watchlist")
public class WatchlistController {

    private UserService userService;
    private MovieService movieService;
    private WatchlistService watchlistService;
    private ReviewService reviewService;

    @Autowired
    public WatchlistController(UserService userService, MovieService movieService,
                               WatchlistService watchlistService, ReviewService reviewService) {
        this.userService = userService;
        this.movieService = movieService;
        this.watchlistService = watchlistService;
        this.reviewService = reviewService;
    }

    @GetMapping(value = "/watchlists/{userId}")
    public ResponseEntity<Set<WatchlistElementDTO>> getWatchlistByUserId(@PathVariable Long userId) {
        User user = this.userService.findUserById(userId);
        Set<WatchlistElementDTO> result = this.watchlistService.getUserWatchlistDTO(user);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/watchlists/{userId}/movies/{movieId}")
    public ResponseEntity<WatchlistElementDTO> getWatchlistElementByUserIdAndMovieId(@PathVariable Long userId,
                                                                                     @PathVariable Long movieId) {
        WatchlistElement foundElement = this.watchlistService.getWatchlistElementByUserIdAndMovieId(userId, movieId);
        return new ResponseEntity<>(WatchlistElementDTO.from(foundElement), HttpStatus.OK);
    }

    @DeleteMapping(value = "/watchlists/{userId}/movies/{movieId}")
    public ResponseEntity<WatchlistElementDTO> removeWatchlistElementByUserIdAndMovieId(@PathVariable Long userId,
                                                                                        @PathVariable Long movieId) {
        User user = this.userService.findUserById(userId);
        WatchlistElement foundElement = this.watchlistService.getWatchlistElementByUserIdAndMovieId(userId, movieId);
        foundElement = this.watchlistService.removeWatchlistElementFromUser(user, foundElement);
        return new ResponseEntity(WatchlistElementDTO.from(foundElement), HttpStatus.OK);
    }

    @PostMapping(
            value = "/watchlists/{userId}/movies/{movieId}/reviews",
            consumes = "application/json",
            produces = "application/json"
    )
    public ResponseEntity<WatchlistElementDTO> addReviewByUserIdAndMovieId(@PathVariable Long userId,
                                                                           @PathVariable Long movieId,
                                                                           @RequestBody ReviewPostRequest request) {
        WatchlistElement foundElement = this.watchlistService.getWatchlistElementByUserIdAndMovieId(userId, movieId);
        Review newReview = new Review(request.rating(), request.reviewBody());
        newReview.setOwner(foundElement);
        WatchlistElementDTO updatedElement = this.watchlistService.addReviewToWatchlistElement(foundElement, newReview);

        return new ResponseEntity<>(updatedElement, HttpStatus.CREATED);
    }

    @GetMapping(value = "/watchlists/elements/{elementId}")
    public ResponseEntity<WatchlistElementDTO> getWatchlistElementById(@PathVariable Long elementId) {
        WatchlistElement foundElement = this.watchlistService.getWatchlistElementById(elementId);
        return new ResponseEntity<>(WatchlistElementDTO.from(foundElement), HttpStatus.OK);
    }

    @PostMapping(
            value = "/watchlists/elements/{elementId}/finished",
            consumes = "application/json",
            produces = "application/json"
    )
    public ResponseEntity<WatchlistElementDTO> setWatchlistElementAsFinished(@PathVariable Long elementId) {
        WatchlistElement foundElement = this.watchlistService.setElementFinishedStatus(elementId, true);
        return new ResponseEntity<>(WatchlistElementDTO.from(foundElement), HttpStatus.OK);
    }

    @PostMapping(
            value = "/watchlists/elements/{elementId}/unfinished",
            consumes = "application/json",
            produces = "application/json"
    )
    public ResponseEntity<WatchlistElementDTO> setWatchlistElementAsNotFinished(@PathVariable Long elementId) {
        WatchlistElement foundElement = this.watchlistService.setElementFinishedStatus(elementId, false);
        return new ResponseEntity<>(WatchlistElementDTO.from(foundElement), HttpStatus.OK);
    }

    @GetMapping(value = "/watchlists/elements/{elementId}/reviews")
    public ResponseEntity<Set<ReviewDTO>> getWatchlistElementReviews(@PathVariable Long elementId) {
        WatchlistElement foundElement = this.watchlistService.getWatchlistElementById(elementId);
        Set<ReviewDTO> result = foundElement.getReviews().stream()
                .map(r -> ReviewDTO.from(r))
                .collect(Collectors.toSet());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/watchlists/elements/{elementId}/average-rating")
    public ResponseEntity<WatchlistElementAverageRatingResponse> getWatchlistElementAverageRating(@PathVariable Long elementId) {
        Integer rating = this.watchlistService.getElementReviewedEntityAverageRating(elementId);
        return new ResponseEntity<>(new WatchlistElementAverageRatingResponse(elementId, rating), HttpStatus.OK);
    }

    @GetMapping(value = "/watchlists/elements/{elementId}/statistics")
    public ResponseEntity<MovieStatisticsResponse> getWatchlistMovieStatistics(@PathVariable Long elementId) {
        Integer rating = this.watchlistService.getElementReviewedEntityAverageRating(elementId);
        Long reviewedEntityId = this.watchlistService.getElementReviewedEntityId(elementId);
        Long usersToWatchCount = this.watchlistService.getElementReviewedEntityMarkedUsersCount(elementId, false);
        Long usersFinishedCount = this.watchlistService.getElementReviewedEntityMarkedUsersCount(elementId, true);
        Long reviewsCount = this.watchlistService.getElementReviewedEntityReviewsCount(elementId);
        MovieStatisticsResponse msr = new MovieStatisticsResponse(reviewedEntityId, rating, usersToWatchCount,
                                                                    usersFinishedCount, reviewsCount);
        return new ResponseEntity<>(msr, HttpStatus.OK);
    }

    @PostMapping(
            value = "/watchlists/elements/{elementId}/reviews",
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<WatchlistElementDTO> postReviewToElementById(@PathVariable Long elementId,
                                                                @RequestBody ReviewPostRequest request) {
        WatchlistElement foundElement = this.watchlistService.getWatchlistElementById(elementId);
        Review newReview = new Review(request.rating(), request.reviewBody());
        WatchlistElementDTO updatedElement = this.watchlistService.addReviewToWatchlistElement(foundElement, newReview);
        return new ResponseEntity<>(updatedElement, HttpStatus.CREATED);
    }

    @GetMapping(value = "/watchlists/elements/{elementId}/reviews/{reviewId}")
    public ResponseEntity<ReviewDTO> getWatchlistElementReviewByReviewId(@PathVariable Long elementId,
                                                                          @PathVariable Long reviewId) {
        Review foundReview = this.reviewService.findReviewById(reviewId);
        ReviewDTO result = ReviewDTO.from(foundReview);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
