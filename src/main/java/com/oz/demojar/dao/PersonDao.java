package com.oz.demojar.dao;

import com.oz.demojar.model.Country;
import com.oz.demojar.model.Passport;
import com.oz.demojar.model.Person;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface PersonDao {

    Person addPerson(String firstName, String lastName, Country country, String position, int age, int boss);

    List<Person> selectAllPeople();

    int deletePersonById(Long id);

    Person updatePersonById(Long id, Person person);

    Person getPersonById(Long id);

    void addPersonToCountry(Person p, Country c);

    Person setPassportToPerson(Passport passport, Person person);

    Collection<Person> findAllAnns();

    Collection<Person> findPersonsWithPassportsByCountry(Long id);

    long findLastId();

}
