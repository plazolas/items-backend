package com.oz.demojar.mysqlDatasource;

import com.oz.demojar.model.Country;
import com.oz.demojar.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

// This will be AUTO IMPLEMENTED by Spring into a Bean called MysqlDataSource
// CRUD refers Create, Read, Update, Delete

@EnableJpaRepositories(basePackages = "com.oz.demojar.dao")
public interface CountryRepository extends JpaRepository<Country, Long> {

    Optional<Country> findByName(String name);

    @Query(value = "SELECT * FROM country c WHERE id = ?1", nativeQuery = true)
    Country getCountryById(int id);

    @Query(value = "SELECT MAX(id) FROM person.country", nativeQuery = true)
    long findLastId();

    @Modifying
    @Transactional
    @Query(value = "UPDATE country c SET name = ?1 WHERE c.id  = ?2",
            countQuery = "SELECT * FROM country WHERE id = ?2",
            nativeQuery = true)
    int updateCountry(String name, Long id);
}
