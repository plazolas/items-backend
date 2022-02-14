package com.oz.demojar.dao;

import com.oz.demojar.model.Passport;
import com.oz.demojar.model.Person;

import java.util.List;
import java.util.Optional;

public interface PassportDao {

    Passport createPassport(Person person);

    List<Passport> selectAllPassports();

    int deletePassportById(Long id);

    Passport getPassportById(Long id);

    int updatePassportById(Long id, Passport passport);

    Passport setPersonToPassport(Passport passport, Person person);

}
