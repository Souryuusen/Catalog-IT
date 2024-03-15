package com.soursoft.catalogit.repository;

import com.soursoft.catalogit.dto.MovieDataDTO;
import com.soursoft.catalogit.entity.Movie;
import com.soursoft.catalogit.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsernameIgnoreCase(String username);
    User findByEmailIgnoreCase(String email);

    @Query("SELECT u.watchlistSet FROM User u where u.userId = ?1")
    Set<Movie> findWatchlistByUserId(Long userId);
}
