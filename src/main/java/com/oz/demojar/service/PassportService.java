package com.oz.demojar.service;

import com.oz.demojar.dao.PassportDao;
import com.oz.demojar.dao.PersonDao;
import com.oz.demojar.model.Passport;
import com.oz.demojar.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PassportService {

    @Qualifier("passport")
    private final PassportDao passportDao;
    @Qualifier("person")
    private final PersonDao personDao;

    @Autowired
    public PassportService(PassportDao passportDao, PersonDao personDao) {
        this.passportDao = passportDao;
        this.personDao = personDao;
    }

    public Passport createPassport(Person person) {
        Passport passport = passportDao.createPassport(person);
        personDao.setPassportToPerson(passport, person);
        return passport;
    }

    public List<Passport> getAllPassports() {
        return passportDao.selectAllPassports();
    }

    public Passport selectPassportById(Long id) {
        return passportDao.getPassportById(id);
    }

    public int deletePassportById(Long id) {
        return passportDao.deletePassportById(id);
    }

    public int updatePassportById(Long id, Passport p) {
        passportDao.updatePassportById(id, p);
        return 1;
    }
}
