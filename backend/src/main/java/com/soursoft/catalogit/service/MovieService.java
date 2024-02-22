package com.soursoft.catalogit.service;

import com.soursoft.catalogit.dto.ScrapedDataDTO;
import com.soursoft.catalogit.entity.*;
import com.soursoft.catalogit.exception.MovieNotFoundException;
import com.soursoft.catalogit.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private MovieRepository repository;

    private DirectorService directorService;
    private WriterService writerService;
    private GenreService genreService;
    private KeywordService keywordService;

    @Autowired
    public MovieService(MovieRepository repository, DirectorService directorService,
                            WriterService writerService, GenreService genreService,
                                KeywordService keywordService) {
        this.repository = repository;
        this.directorService = directorService;
        this.writerService = writerService;
        this.genreService = genreService;
        this.keywordService = keywordService;
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

    @Transactional
    public Movie createFromData(ScrapedDataDTO data) {
        Movie.MovieBuilder movieBuilder = new Movie.MovieBuilder().reset();
        movieBuilder
                .withIdentifier(data.getMovieIdentifier())
                .withTitle(data.getTitle())
                .withOriginalTitle(data.getOriginalTitle())
                .withReleaseDate(data.getReleaseDate())
                .withRuntime(data.getRuntime())
                .withLanguage(data.getLanguage())
                .withCountry(data.getCountry())
                .withDirectors(
                        data.getDirectorsSet().stream()
                            .map(director -> directorService.ensureDirectorExist(director))
                            .collect(Collectors.toSet()))
                .withWriters(
                        data.getWritersSet().stream()
                            .map(writer -> writerService.ensureWriterExist(writer))
                            .collect(Collectors.toSet()))
                .withGenres(
                        data.getGenresSet().stream()
                            .map(genre -> genreService.ensureGenreExist(genre))
                            .collect(Collectors.toSet()))
                .withKeywords(
                        data.getKeywordsSet().stream()
                            .map(keyword -> keywordService.ensureKeywordExist(keyword))
                            .collect(Collectors.toSet()))
                .withStars(data.getStarActorsSet())
                .withCovers(data.getCoverUrlsSet());

        return movieBuilder.build();
    }

    @Transactional
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
