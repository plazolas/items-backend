package com.oz.demojar.dao;

import com.oz.demojar.model.Country;
import com.oz.demojar.model.Passport;
import com.oz.demojar.model.Person;
import com.oz.demojar.mysqlDatasource.CountryRepository;
import com.oz.demojar.mysqlDatasource.PassportRepository;
import com.oz.demojar.mysqlDatasource.PersonRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.validation.FieldError;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.*;
@Slf4j
@Primary
@Repository("person")
class PersonDaoImpl implements PersonDao {

    @Autowired // This means to get the bean called PersonRepository Which is auto-generated by Spring
    private transient PersonRepository personRepository;
    @Autowired
    private transient CountryRepository countryRepository;
    @Autowired
    private transient PassportRepository passportRepository;

    @PersistenceContext
    private EntityManager em;

    @Override
    public Person addPerson(String firstName, String lastName, Country country, String position, Integer age, Integer boss) {

        try {
            Person person = new Person(firstName, lastName, country, position, age, boss);
            Person newPerson = personRepository.save(person);
            return newPerson;
        } catch (ConstraintViolationException e) {
            Set<ConstraintViolation<?>> errors;
            errors = e.getConstraintViolations();
            errors.forEach(error -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getMessage();
                log.error("field: " + fieldName + " msg: " + errorMessage);
            });
            return null;
        } catch (Exception e) {
            log.error("ERROR: " + e.getMessage());
            e.getStackTrace();
            return null;
        }

    }

    @Override
    public Page<Person> selectAllPersonsPage(Pageable paging) {
        Page<Person> persons = personRepository.findAll(paging);
        return persons;
    }

    @Override
    public List<Person> selectAllPersons() {
        List<Person> persons = personRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        // Query query = em.createQuery("From Person");
        //List<Person> persons = query.getResultList();
        //Collections.sort(persons, Comparator.comparing(p -> (p.getLastName() == null) ? "" : p.getLastName().toLowerCase()));
        //return persons;
        // sorted without lower case
        //return  personRepository.findAll().stream().sorted(Comparator.comparing(Person::getLastName)).collect(Collectors.toList());
        return persons;
    }

    @Override
    public int deletePersonById(Long id) {
        Optional<Person> person = getPersonById(id);
        if (person.isPresent()) {
            personRepository.delete(person.get());
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public Person updatePerson(Person person) {
        log.debug(person.toString());
        long cid = (person.getCountry() == null) ? 50 : person.getCountry().getId();

            int success = personRepository.updatePerson(
                    person.getId(),
                    person.getFirstName(),
                    person.getLastName(),
                    cid,
                    person.getAge(),
                    person.getPosition(),
                    person.getBoss(),
                    person.getUpdated()
            );
        if(success > 0) log.info("person update success");
        return (success > 0) ? person : null;
    }

    @Override
    public Person updatePersonById(Long id, Person person) {;

        Person oldPerson = personRepository.getById(id);

        if(!oldPerson.isValid()) {
            return null;
        } else {
            if(person.getFirstName()   == null) person.setFirstName(oldPerson.getFirstName());
            if(person.getLastName()   == null) person.setLastName(oldPerson.getLastName());
            if(person.getCountry()   == null) person.setCountry(oldPerson.getCountry());
            if(person.getPassport()  == null && oldPerson.getPassport() != null) person.setPassport(oldPerson.getPassport());
            if(person.getAge()  == null && oldPerson.getAge() != null) person.setAge(oldPerson.getAge());
            if(person.getPosition()  == null && oldPerson.getPosition() != null) person.setPosition(oldPerson.getPosition());
            if(person.getBoss()  == null && oldPerson.getBoss() != null) person.setPosition(oldPerson.getPosition());
        }

            int result = personRepository.updatePerson(
                    person.getId(),
                    person.getFirstName(),
                    person.getLastName(),
                    person.getCountry().getId(),
                    person.getAge(),
                    person.getPosition(),
                    person.getBoss(),
                    person.getUpdated()
            );
            if (result == 0) throw new RuntimeException(PersonDaoImpl.class.getName() + ":updatePersonById");


        return person;
    }

    @Override
    public Optional<Person> getPersonById(Long id) {
        return personRepository.findById(id);
    }

    @Override
    public Person addPersonToCountry(Person p, Country c) {
        p.setCountry(c);
        return personRepository.save(p);
    }

    public Person setPassportToPerson(Passport passport, Person person) {
        person.setPassport(passport);
        return personRepository.save(person);
    }

    public Passport setPersonToPassport(Passport passport, Person person) {
        passport.setPerson(person);
        return passportRepository.save(passport);
    }

    public Collection<Person> findAllAnns() {
        return personRepository.findAllAnns();
    }

    public Collection<Person> findPersonsWithPassportsByCountry(Long country_id) {
        return personRepository.findPersonsWithPassportsByCountry(country_id);
    }

    public long findLastId() {
        return personRepository.findLastId();
    }

    public static Person deserialize(String fileName) throws Exception {
        FileInputStream file = new FileInputStream(fileName);
        ObjectInputStream in = new ObjectInputStream(file);
        Person book = (Person) in.readObject();
        in.close();
        file.close();
        return book;
    }

}

