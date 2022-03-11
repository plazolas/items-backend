package com.oz.demojar.service;

import com.oz.demojar.dao.PersonDao;
import com.oz.demojar.model.Country;
import com.oz.demojar.model.Passport;
import com.oz.demojar.model.Person;

import com.oz.demojar.mysqlDatasource.CountryRepository;
import com.oz.demojar.mysqlDatasource.PassportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import java.util.stream.Collectors;

import javax.persistence.*;

import java.util.*;

@Service
public class PersonService {

    @Qualifier("person")
    private final PersonDao personRepository;

    @PersistenceContext
    private final EntityManager em;

    @Autowired
    private PassportRepository passportRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    public PersonService(EntityManager entityManager, PersonDao personDao) {
        this.personRepository = personDao;
        this.em = entityManager;
    }

    public Person addPerson(String firstName, String lastName, Country country) {
        return personRepository.addPerson(firstName, lastName, country);
    }

    public List<Person> getAllPeople() {
        List<Person> persons = personRepository.selectAllPeople();
        return persons;
    }

    public Person getPersonById(Long id) {
        return personRepository.getPersonById(id);
    }

    public long findLastId() {
        return personRepository.findLastId();
    }

    public int deletePersonById(Long id) {
        return personRepository.deletePersonById(id);
    }

    public Person updatePersonById(Long id, Person p) {
        return personRepository.updatePersonById(id, p);
    }

    public void addPersonToCountry(Person p, Country c) {
        personRepository.addPersonToCountry(p,c);
    }

    public Collection<Person> findAllAnns() {
        return personRepository.findAllAnns();
    }

    public Collection<Person> findPersonsWithPassportsByCountry(Long id) {
        return personRepository.findPersonsWithPassportsByCountry(id);
    }

    public long findLast() {
        return personRepository.findLastId();
    }

    public List<Person> findPaginated(int pageNumber, int pageSize) {
        if (pageNumber == 0) pageNumber = 1;
        Query query = em.createQuery("From Person");
        query.setFirstResult((pageNumber-1) * pageSize);
        query.setMaxResults(pageSize);
        if (pageSize > query.getMaxResults()/pageNumber) {
            pageSize = Math.round(query.getMaxResults()/pageNumber) - 1;
            query.setMaxResults(pageSize);
        }
        List<Person> personList = query.getResultList();
        return personList;
    }

    public List<Person> massUpdate() {
        Query queryPersons = em.createNativeQuery(
                "Select p.id, p.country_id, p.passport_id from person p, passport t where p.passport_id = t.id " +
                        "and p.country_id != t.country_id");

//        "Select p.id as pid, p.country_id pcid, t.id as pid, t.country_id as tcid from person p, passport t where p.passport_id = t.id " +
//                "and p.country_id != t.country_id");

        List<Object> result = (List<Object>) queryPersons.getResultList();
        Iterator itr = result.iterator();
        ArrayList<Person> personList = new ArrayList<>();
        while(itr.hasNext()){
            Object[] obj = (Object[]) itr.next();
            //now you have one array of Object for each row
            Integer pid = Integer.parseInt(String.valueOf(obj[0]));
            Integer c_id = Integer.parseInt(String.valueOf(obj[1]));
            Integer pass_id = Integer.parseInt(String.valueOf(obj[2]));

            Long id = pid.longValue();
            Long country_id = c_id.longValue();
            Long passport_id = pass_id.longValue();

            Person person = personRepository.getPersonById(id);
            Passport passport = passportRepository.getById(passport_id);

            System.out.println(person);
            System.out.println(passport);

            person.setCountry(passport.getCountry());
            personRepository.updatePersonById(person.getId(),person);

            personList.add(person);
        }
        return personList;

    }

}
