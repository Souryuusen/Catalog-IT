package com.soursoft.catalogit.service;

import com.soursoft.catalogit.entity.Genre;
import com.soursoft.catalogit.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GenreService {

    private GenreRepository repository;

    @Autowired
    public GenreService(GenreRepository repository) {
        this.repository = repository;
    }

    public Genre save(Genre genre) {
        return this.repository.save(genre);
    }

    public Optional<Genre> findGenreByNameIgnoreCase(String name) {
        return this.repository.findGenreByNameIgnoreCase(name);
    }
}
