package com.oz.demojar.mysqlDatasource;

import com.oz.demojar.model.Country;
import com.oz.demojar.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Collection;

// This will be AUTO IMPLEMENTED by Spring into a Bean called MysqlDataSource
// CRUD refers Create, Read, Update, Delete

@EnableJpaRepositories(basePackages = "com.oz.demojar.dao")
public interface CountryRepository extends JpaRepository<Country, Long> {

    Country findByName(String name);

    @Query(value = "SELECT * FROM country c WHERE id = ?1", nativeQuery = true)
    Country getRandomCountry(int id);

    @Query(value = "SELECT MAX(id) FROM person.country", nativeQuery = true)
    long findLastId();
}
