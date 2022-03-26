package com.oz.demojar.mysqlDatasource;

import com.oz.demojar.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Optional;

@EnableJpaRepositories(basePackages = "com.oz.demojar.dao")
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String userName);

    @Query(value = "SELECT id FROM User ORDER BY RAND() LIMIT 1", nativeQuery = true)
    User getRandomUser(int id);

    @Query(value = "SELECT MAX(id) FROM person.person", nativeQuery = true)
    long findLastId();
}
