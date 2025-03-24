package com.calendarapp.auth.repository;

import com.calendarapp.auth.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, Long> {

    Optional<User> findByLogin(String login);

    Boolean existsByUsername(String username);

    Boolean existsByLogin(String login);
}
