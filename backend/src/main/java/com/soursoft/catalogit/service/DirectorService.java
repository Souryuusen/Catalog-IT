package com.soursoft.catalogit.service;

import com.soursoft.catalogit.entity.Director;
import com.soursoft.catalogit.repository.DirectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DirectorService {

    private DirectorRepository repository;

    @Autowired
    public DirectorService(DirectorRepository repository) {
        this.repository = repository;
    }

    public Director save(Director director) {
        return this.repository.save(director);
    }

    public Optional<Director> findDirectorByNameIgnoreCase(String name) {
        return this.repository.findDirectorByNameIgnoreCase(name);
    }

    public Director ensureDirectorExist(String directorName) {
        var fetchedDirector = this.repository.findDirectorByNameIgnoreCase(directorName);
        if(fetchedDirector.isPresent()) {
            return fetchedDirector.get();
        } else {
            return this.repository.save(new Director(directorName));
        }
    }
}
