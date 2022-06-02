package com.oz.demojar.dao;

import com.oz.demojar.model.Country;
import com.oz.demojar.model.Passport;
import com.oz.demojar.model.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface PersonDao {

    Person addPerson(String firstName, String lastName, Country country, String position, Integer age, Integer boss);

    List<Person> selectAllPersons();

    int deletePersonById(Long id);

    Person updatePerson(Person person);

    Person updatePersonById(Long id, Person person);

    Optional<Person> getPersonById(Long id);

    Person addPersonToCountry(Person p, Country c);

    Person setPassportToPerson(Passport passport, Person person);

    Collection<Person> findAllAnns();

    Collection<Person> findPersonsWithPassportsByCountry(Long id);

    long findLastId();

    Page selectAllPersonsPage(Pageable paging);

}
