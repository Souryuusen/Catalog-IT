package com.soursoft.catalogit.repository;

import com.soursoft.catalogit.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    boolean existsByMovieIdentifier(String movieIdentifier);

    Movie findByMovieIdentifier(String identifier);
    Movie findByMovieId(Long id);
    List<Movie> findByTitle(String title);

}
