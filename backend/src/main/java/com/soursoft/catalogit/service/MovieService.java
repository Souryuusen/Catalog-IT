package com.soursoft.catalogit.service;

import com.soursoft.catalogit.entity.Movie;
import com.soursoft.catalogit.exception.MovieNotFoundException;
import com.soursoft.catalogit.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    private MovieRepository repository;

    @Autowired
    public MovieService(MovieRepository repository) {
        this.repository = repository;
    }

    public boolean verifyMovieExistsByIdentifier(String movieIdentifier) {
        return this.repository.existsByMovieIdentifier(movieIdentifier);
    }

    public Movie findMovieById(Long id) {
        return repository.findByMovieId(id);
    }

    public Movie findMovieByIdentifier(String identifier) {
        return repository.findByMovieIdentifier(identifier);
    }

    public List<Movie> findMoviesByTitle(String title) {
        return repository.findByTitle(title);
    }

    public List<Movie> findAllMovies() {
        return this.repository.findAll();
    }

    public Movie save(Movie movie) {
        return this.repository.save(movie);
    }

    public Movie update(Movie movie) {
        if(this.repository.existsById(movie.getMovieId())) {
            return this.repository.save(movie);
        } else {
            throw new MovieNotFoundException("Update failed - Movie has not been found in database");
        }
    }

    public long delete(Movie movie) {
        if(this.repository.existsById(movie.getMovieId())) {
            this.repository.delete(movie);
            return movie.getMovieId();
        } else {
            throw new MovieNotFoundException("Delete failed - Movie has not been found in database");
        }
    }

}
