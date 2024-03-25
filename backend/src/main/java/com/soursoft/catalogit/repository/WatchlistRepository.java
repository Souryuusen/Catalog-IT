package com.soursoft.catalogit.repository;

import com.soursoft.catalogit.entity.Movie;
import com.soursoft.catalogit.entity.User;
import com.soursoft.catalogit.entity.WatchlistElement;
import org.springframework.data.jpa.repository.JpaRepository;
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

}
