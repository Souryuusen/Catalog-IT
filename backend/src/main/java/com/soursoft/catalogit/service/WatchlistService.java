package com.soursoft.catalogit.service;

import com.soursoft.catalogit.dto.ReviewDTO;
import com.soursoft.catalogit.dto.WatchlistElementDTO;
import com.soursoft.catalogit.entity.Movie;
import com.soursoft.catalogit.entity.Review;
import com.soursoft.catalogit.entity.User;
import com.soursoft.catalogit.entity.WatchlistElement;
import com.soursoft.catalogit.exception.WatchlistElementNotFoundException;
import com.soursoft.catalogit.exception.WatchlistNotFoundException;
import com.soursoft.catalogit.repository.WatchlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class WatchlistService {

    private WatchlistRepository repository;

    private UserService userService;
    private MovieService movieService;
    private ReviewService reviewService;

    @Autowired
    public WatchlistService(WatchlistRepository repository, UserService userService,
                                MovieService movieService, ReviewService reviewService) {
        this.repository = repository;

        this.userService = userService;
        this.movieService = movieService;
        this.reviewService = reviewService;
    }

    public WatchlistElement getWatchlistElementById(Long elementId) {
        Optional<WatchlistElement> optionalElement = this.repository.findByWatchlistId(elementId);

        if(optionalElement.isPresent()) {
            return optionalElement.get();
        } else {
            throw new WatchlistElementNotFoundException(String.format("Watchlist element by ID: %d not found!", elementId));
        }
    }

    public WatchlistElement getWatchlistElementByUserIdAndMovieId(Long userId, Long movieId) {
        User user = this.userService.findUserById(userId);
        Movie movie = this.movieService.findMovieById(movieId);
        Optional<WatchlistElement> optionalElement = this.getWatchlistElementByUserAndMovie(user, movie);

        if(optionalElement.isPresent()) {
            WatchlistElement foundElement = optionalElement.get();
            return foundElement;
        } else {
            throw new WatchlistElementNotFoundException(user, movie);
        }
    }

    public Set<WatchlistElement> getUserWatchlist(User user) {
        var result = this.repository.findAllByOwner(user);
        if(result.isPresent()) {
            return result.get();
        } else {
            throw new WatchlistNotFoundException("No watchlist found for user id" + user.getUserId());
        }
    }

//    @Transactional
    public WatchlistElement createNewWatchlistElementForUser(User user, Movie movie){
        return this.save(WatchlistElement.from(user, movie));
    }

//    @Transactional
    public WatchlistElement removeWatchlistElementFromUser(User user, WatchlistElement element) {
        if(element.getReviews().size() > 0) {
            for(Review r: element.getReviews()) {
                this.reviewService.removeReview(r);
            }
        }
        user = this.userService.removeWatchlistElementFromUser(user, element);
        this.repository.delete(element);
        System.out.println(element);
        return element;
    }

    public Set<WatchlistElementDTO> getUserWatchlistDTO(User user) {
        Set<WatchlistElement> userWatchlist = this.getUserWatchlist(user);

        Set<WatchlistElementDTO> result = userWatchlist.stream()
                .map(element -> WatchlistElementDTO.from(element))
                .collect(Collectors.toSet());
        return result;
    }

    public Optional<WatchlistElement> getWatchlistElementByUserAndMovie(User user, Movie movie) {
        return this.repository.findElementByUserAndMovie(user, movie);
    }

    @Transactional
    public WatchlistElementDTO addReviewToWatchlistElement(WatchlistElement element, Review review) {
        Review persistedReview = this.reviewService.save(review);
        element.addReview(persistedReview);
        if(element.getRating() == 0 && persistedReview.getRating() > 0) {
            element.setRating(persistedReview.getRating());
        }
        WatchlistElement updatedElement = this.repository.save(element);
        return WatchlistElementDTO.from(updatedElement);
    }

    @Transactional
    public WatchlistElement setElementFinishedStatus(Long watchlistElementId, Boolean finished) {
        if(this.repository.existsById(watchlistElementId)) {
            this.repository.updateElementFinished(watchlistElementId, finished);
            Optional<WatchlistElement> optionalWatchlistElement = this.repository.findByWatchlistId(watchlistElementId);
            if(optionalWatchlistElement.isPresent()) {
                return optionalWatchlistElement.get();
            } else {
                throw new WatchlistElementNotFoundException("Watchlist element by ID " + watchlistElementId + " does not exits");
            }
        } else {
            throw new WatchlistElementNotFoundException("Watchlist element by ID " + watchlistElementId + " does not exits");
        }
    }

    public Integer getElementReviewedEntityAverageRating(Long watchlistElementId) {
        if(this.repository.existsById(watchlistElementId)) {
            Optional<WatchlistElement> optionalWatchlistElement = this.repository.findByWatchlistId(watchlistElementId);
            if(optionalWatchlistElement.isPresent()) {
                WatchlistElement foundElement = optionalWatchlistElement.get();
                return this.repository.getMovieAverageRating(foundElement.getReviewedEntity().getMovieId());
            } else {
                throw new WatchlistElementNotFoundException("Watchlist element by ID " + watchlistElementId + " does not exits");
            }
        } else {
            throw new WatchlistElementNotFoundException("Watchlist element by ID " + watchlistElementId + " does not exits");
        }
    }

    public Long getElementReviewedEntityMarkedUsersCount(Long elementId, Boolean finishedStatus) {
        if(this.repository.existsById(elementId)) {
            Long reviewedEntityId = this.repository.getWatchlistEntityMovieId(elementId);
            return this.repository.getUsersCountByFinishedStatus(reviewedEntityId, finishedStatus);
        } else {
            throw new WatchlistElementNotFoundException("Watchlist element by ID " + elementId + " does not exits");
        }
    }

    public Long getElementReviewedEntityId(Long elementId) {
        if(this.repository.existsById(elementId)) {
            return this.repository.getWatchlistEntityMovieId(elementId);
        } else {
            throw new WatchlistElementNotFoundException("Watchlist element by ID " + elementId + " does not exits");
        }
    }

    public Long getElementReviewedEntityReviewsCount(Long elementId) {
        if(this.repository.existsById(elementId)) {
            Long reviewedEntityId = this.repository.getWatchlistEntityMovieId(elementId);
            return this.repository.getReviewsCountByReviewedEntityId(reviewedEntityId);
        } else {
            throw new WatchlistElementNotFoundException("Watchlist element by ID " + elementId + " does not exits");
        }
    }

    public WatchlistElement save(WatchlistElement element) {
        return this.repository.save(element);
    }


}