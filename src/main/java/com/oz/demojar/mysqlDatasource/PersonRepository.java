package com.oz.demojar.mysqlDatasource;

import com.oz.demojar.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;

@EnableJpaRepositories(basePackages = "com.oz.demojar.dao")
public interface PersonRepository extends JpaRepository<Person, Long>, PagingAndSortingRepository<Person, Long> {

    @Query(value = "SELECT * FROM person p WHERE p.first_name  = 'ann'", nativeQuery = true)
    Collection<Person> findAllAnns();

    @Query(value = "SELECT * FROM person p WHERE p.country_id  = ?1 AND passport_id > 0",
            countQuery = "SELECT count(*) FROM person p WHERE p.country_id  = ?1 AND passport_id > 0",
            nativeQuery = true)
    Collection<Person> findPersonsWithPassportsByCountry(Long country_id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE person p SET last_name = ?1, first_name = ?2 WHERE p.id  = ?3",
            countQuery = "SELECT * FROM person WHERE id = ?3",
            nativeQuery = true)
    int updateShortById(String lastname, String firstname, Long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE person p SET " +
            "first_name = ?2,  " +
            "last_name = ?3,  " +
            "country_id = ?4,  " +
            "age = ?5,  " +
            "position = ?6,  " +
            "boss = ?7,  " +
            "updated = ?8  " +
            "WHERE p.id  = ?1",
            countQuery = "SELECT * FROM person WHERE id = ?1",
            nativeQuery = true)
    int updatePerson(
            Long id,
            String firstName,
            String lastName,
            long country_id,
            int age,
            String position,
            int boss,
            LocalDateTime updated
    );

    @Query(value = "SELECT person.id FROM person.person ORDER BY person.updated DESC LIMIT 1", nativeQuery = true)
    long findLastId();

}
