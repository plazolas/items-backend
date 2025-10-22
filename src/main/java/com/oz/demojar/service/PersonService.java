package com.oz.demojar.service;

import com.oz.demojar.dao.PassportDao;
import com.oz.demojar.dao.PersonDao;
import com.oz.demojar.model.Country;
import com.oz.demojar.model.Passport;
import com.oz.demojar.model.Person;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import javax.persistence.*;
import java.util.*;

/*
 import org.springframework.data.domain.Page;
 import org.springframework.data.domain.Pageable;
 import org.springframework.data.repository.PagingAndSortingRepository;
 import java.util.stream.Collectors;
*/
@Service
public class PersonService {

    @Qualifier("person")
    private final PersonDao personDao;

    @Qualifier("passport")
    private final PassportDao passportRepository;

    @PersistenceContext
    private final EntityManager em;

    @Autowired
    public PersonService(EntityManager entityManager, PersonDao personDao, PassportDao passportDao) {
        this.personDao = personDao;
        this.passportRepository = passportDao;
        this.em = entityManager;
    }

    public Person addPerson(String firstName, String lastName, Country country, String position, int age, int boss) {
        return personDao.addPerson(firstName, lastName, country, position, age, boss);
    }

    public List<Person> getAllPersons() {
        return personDao.selectAllPersons();
    }

    public List<Person> getAllPersonsByPage(Integer pageNo, Integer pageSize, String sortBy) {

        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Person> pagedResult = personDao.selectAllPersonsPage(paging);
        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<>();
        }
    }

    public Optional<Person> getPersonById(Long id) {
        Person person = personDao.getPersonById(id)
                .orElseThrow(() -> new NoSuchElementException("Could not find Person by id=" + id));
        return Optional.of(person);
    }

    public Person updatePerson(Person person) {
        return personDao.updatePerson(person);
    }

    public Person updatePersonById(Long id, Person person) {
        return personDao.updatePersonById(id, person);
    }

    public long findLastId() { return personDao.findLastId(); }

    public int deletePersonById(Long id) {
        return personDao.deletePersonById(id);
    }

    public Person addPersonToCountry(Person person, Country country) {
        return personDao.addPersonToCountry(person, country);
    }

    public Collection<Person> findAllAnns() {
        return personDao.findAllAnns();
    }

    public Collection<Person> findPersonsWithPassportsByCountry(Long id) {
        return personDao.findPersonsWithPassportsByCountry(id);
    }

    public List<Person> findPaginated(int pageNumber, int pageSize) {
        if (pageNumber == 0) pageNumber = 1;
        Query query = em.createQuery("From Person");
        query.setFirstResult((pageNumber-1) * pageSize);
        query.setMaxResults(pageSize);
        if (query.getMaxResults() > 0 && pageSize > query.getMaxResults()/pageNumber) {
            pageSize = Math.round((float) query.getMaxResults() /pageNumber) - 1;
            query.setMaxResults(pageSize);
        }
        // List<Person> lp =  r.stream().map(p -> p = (Person) p).collect(Collectors.toList());
        List result = query.getResultList();
        ArrayList<Person> personList = new ArrayList<>();
        while(result.iterator().hasNext()){
            Person p = (Person) result.iterator().next();
            personList.add(p);
        }
        return personList;
    }

    public List<Person> massUpdate() {
        Person person = new Person();
        Query queryItems = em.createNativeQuery(
                "Select p.id, p.country_id, p.passport_id " +
                        "from person p, passport t where p.passport_id = t.id " +
                        "and p.country_id != t.country_id");

//        "Select p.id as pid, p.country_id pcid, t.id as pid, t.country_id as tcid from person p, passport t where p.passport_id = t.id " +
//                "and p.country_id != t.country_id");

        List result = queryItems.getResultList();
        ArrayList<Person> personList = new ArrayList<>();
        while(result.iterator().hasNext()){
            Object[] obj = (Object[]) result.iterator().next();
            // now you have one array of Object for each row
            Long id = Long.parseLong(String.valueOf(obj[0]));
            Long passport_id = Long.parseLong(String.valueOf(obj[2]));
            // Long c_id = Long.parseLong(String.valueOf(obj[1]));

            Optional<Person> personOpt = personDao.getPersonById(id);
            if(personOpt.isPresent()) {
                person = personOpt.get();
            }
            Passport passport = passportRepository.getPassportById(passport_id);

            person.setCountry(passport.getCountry());
            personDao.updatePersonById(person.getId(), person);

            personList.add(person);
        }
        return personList;

    }

    public List<String> searchAllItems(String searchTerm) {

        String[] terms = searchTerm.split("\\+");

        ArrayList<String> personList = new ArrayList<>();
        for (String term : terms) {
            Query queryItems = em.createNativeQuery(
                    "Select p.id, p.first_name, p.last_name, p.position from person p where " +
                            " p.first_name like '%" + term + "%' or " +
                            " p.last_name like '%" + term + "%' or " +
                            " p.position like '%" + term + "%' "
            );
            List result = queryItems.getResultList();
            for (Object o : result) {
                Object[] obj = (Object[]) o;
                // now you have one array of Object for each row
                String first = obj[0].toString();
                String last = String.valueOf(obj[1]);
                String position = (obj[2] == null) ? "janitor" : String.valueOf(obj[2]);
                personList.add(first + " " + last + " " + position);
            }
        }
        return personList;

    }

}
