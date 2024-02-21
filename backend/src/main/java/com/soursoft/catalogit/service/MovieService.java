package com.soursoft.catalogit.service;

import com.soursoft.catalogit.entity.Director;
import com.soursoft.catalogit.entity.Genre;
import com.soursoft.catalogit.entity.Movie;
import com.soursoft.catalogit.entity.Writer;
import com.soursoft.catalogit.exception.MovieNotFoundException;
import com.soursoft.catalogit.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class MovieService {

    private MovieRepository repository;

    private DirectorService directorService;
    private WriterService writerService;
    private GenreService genreService;

    @Autowired
    public MovieService(MovieRepository repository, DirectorService directorService,
                            WriterService writerService, GenreService genreService) {
        this.repository = repository;
        this.directorService = directorService;
        this.writerService = writerService;
        this.genreService = genreService;
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
        Set<Director> directorSet = movie.getDirectors();
        Set<Writer> writerSet = movie.getWriters();
        Set<Genre> genreSet = movie.getGenres();

        for(Director director : directorSet) {
            var fetchedDirector = directorService.findDirectorByNameIgnoreCase(director.getName());
            if(fetchedDirector.isEmpty()) {
                directorService.save(director);
            } else {
                directorSet.remove(director);
                directorSet.add(fetchedDirector.get());
            }
        }
        for(Writer writer : writerSet) {
            var fetchedWriter = writerService.findWriterByNameIgnoreCase(writer.getName());
            if(fetchedWriter.isEmpty()) {
                writerService.save(writer);
            } else {
                writerSet.remove(writer);
                writerSet.add(fetchedWriter.get());
            }
        }
        for(Genre genre : genreSet) {
            var fetchedGenre = genreService.findGenreByNameIgnoreCase(genre.getName());
            if(fetchedGenre.isEmpty()) {
                genreService.save(genre);
            } else {
                genreSet.remove(genre);
                genreSet.add(fetchedGenre.get());
            }
        }
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
