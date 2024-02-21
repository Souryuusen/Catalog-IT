package com.soursoft.catalogit.service;

import com.soursoft.catalogit.entity.Director;
import com.soursoft.catalogit.entity.Writer;
import com.soursoft.catalogit.repository.WriterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WriterService {

    private WriterRepository repository;

    @Autowired
    public WriterService(WriterRepository repository) {
        this.repository = repository;
    }

    public Writer save(Writer writer) {
        return this.repository.save(writer);
    }

    public Optional<Writer> findWriterByNameIgnoreCase(String name) {
        return this.repository.findWriterByNameIgnoreCase(name);
    }
}
