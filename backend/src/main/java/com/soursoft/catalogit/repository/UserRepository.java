package com.soursoft.catalogit.repository;

import com.soursoft.catalogit.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Long, User> {

    User findByUsername(String username);
    User findByEmail(String email);

}
