package com.soursoft.catalogit.repository;

import com.soursoft.catalogit.entity.Movie;
import com.soursoft.catalogit.entity.User;
import com.soursoft.catalogit.entity.WatchlistElement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface WatchlistRepository extends JpaRepository<WatchlistElement, Long> {

    Optional<WatchlistElement> findByWatchlistId(Long watchlistId);

    Optional<Set<WatchlistElement>> findAllByOwner(User user);

    @Query("SELECT element FROM WatchlistElement element WHERE element.owner = ?1 AND element.reviewedEntity = ?2")
    Optional<WatchlistElement> findElementByUserAndMovie(User user, Movie movie);

    @Modifying()
    @Query("UPDATE WatchlistElement element SET element.finished = ?2 WHERE element.watchlistId = ?1")
    void updateElementFinished(Long watchlistId, Boolean finished);

    @Query("SELECT AVG(e.rating) FROM WatchlistElement e WHERE e.reviewedEntity.movieId = ?1 AND e.rating > 0")
    Integer getMovieAverageRating(Long movieId);

    @Query("SELECT e.reviewedEntity.movieId FROM WatchlistElement e WHERE e.watchlistId = ?1")
    Long getWatchlistEntityMovieId(Long watchlistId);

    @Query("SELECT COUNT(e) FROM WatchlistElement e WHERE e.reviewedEntity.movieId = ?1 AND e.finished = ?2")
    Long getUsersCountByFinishedStatus(Long movieId, Boolean markedStatus);

    @Query("""
           SELECT COUNT(r) FROM WatchlistElement e, Review r 
            WHERE e.reviewedEntity.movieId = ?1 AND e.watchlistId = r.owner.watchlistId
           """)
    Long getReviewsCountByReviewedEntityId(Long movieId);

//    boolean existsById(Long elementId);
}
