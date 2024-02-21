package com.soursoft.catalogit.repository;

import com.soursoft.catalogit.entity.Writer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WriterRepository extends JpaRepository<Writer, Long> {

    Optional<Writer> findWriterByNameIgnoreCase(String name);

}
