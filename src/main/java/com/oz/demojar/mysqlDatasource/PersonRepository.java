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

// This will be AUTO IMPLEMENTED by Spring into a Bean called MysqlDataSource
// CRUD refers Create, Read, Update, Delete

//@EnableJpaRepositories(basePackages = "com.oz.demojar.dao")
public interface PersonRepository extends JpaRepository<Person, Long> {

    @Query(value = "SELECT * FROM person p WHERE p.first_name  = 'ann'", nativeQuery = true)
    public Collection<Person> findAllAnns();

    @Query(value = "SELECT * FROM person p WHERE p.country_id  = ?1 AND passport_id > 0",
            countQuery = "SELECT count(*) FROM person p WHERE p.country_id  = ?1 AND passport_id > 0",
            nativeQuery = true)
    public Collection<Person> findPersonsWithPassportsByCountry(Long country_id);

    @Query(value = "SELECT * FROM person p WHERE p.id = ?1", nativeQuery = true)
    public Person findRandomFirstName(int id);

    @Query(value = "SELECT * FROM person p WHERE p.id = ?1", nativeQuery = true)
    public Person findRandomLastName(int id);

    @Query(value = "SELECT MAX(id) FROM person", nativeQuery = true)
    public long findLastId();

}
