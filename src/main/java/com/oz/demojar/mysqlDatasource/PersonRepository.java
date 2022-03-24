package com.oz.demojar.mysqlDatasource;

import com.oz.demojar.model.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Collection;
import java.util.List;

@EnableJpaRepositories(basePackages = "com.oz.demojar.dao")
public interface PersonRepository extends JpaRepository<Person, Long> {

    @Query(value = "SELECT * FROM person p WHERE p.first_name  = 'ann'", nativeQuery = true)
    Collection<Person> findAllAnns();

    @Query(value = "SELECT * FROM person p WHERE p.country_id  = ?1 AND passport_id > 0",
            countQuery = "SELECT count(*) FROM person p WHERE p.country_id  = ?1 AND passport_id > 0",
            nativeQuery = true)
    Collection<Person> findPersonsWithPassportsByCountry(Long country_id);

    @Query(value = "SELECT MAX(id) FROM person", nativeQuery = true)
    long findLastId();

}
