package com.oz.demojar.dto;

import com.oz.demojar.model.Country;
import com.oz.demojar.model.Passport;
import com.oz.demojar.model.Person;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class PersonDTO implements Serializable {
    private Long id;
    private String firstname;
    private String lastname;
    private String position;
    private Integer age;
    private Integer boss;
    private String countryName;
    private Integer countryId;
    private String passportNumber;
    private Integer passportId;

    public Person convertToEntity(PersonDTO personDetails) throws NoSuchElementException {

        Person person = new Person();
        Country country = new Country();
        Passport passport = new Passport();

        person.setId(personDetails.getId());
        person.setFirstName(personDetails.getFirstname());
        person.setLastName(personDetails.getLastname());
        person.setCountry(country);
        person.setAge(personDetails.getAge() == null ? 0 : personDetails.getAge());
        person.setPosition(personDetails.getPosition() == null ? "Management" : personDetails.getPosition());
        person.setBoss(personDetails.getBoss() == null ? 167 : personDetails.getBoss());
        person.setUpdated(LocalDateTime.now());

//        Passport passport = (personDetails.getPassportId() == null) ? null :
//                new Passport(personDetails.getPassportId().longValue(), personDetails.getPassportNumber(), person, country);
//        if(passport != null) passport.setPerson(person);
//        person.setPassport(passport);

        if(personDetails.getCountryId() > 0 && personDetails.getCountryName().length() > 5) {
            country.setId(personDetails.getCountryId().longValue());
            country.setName(personDetails.getCountryName());
        } else {
            country.setId(50L);
            country.setName("United States");
        }

        if(personDetails.getPassportId() != null && personDetails.getPassportNumber() != null &&
                personDetails.getPassportId() > 0 && personDetails.getPassportNumber().length() > 4) {
            passport.setId(personDetails.getPassportId().longValue());
            passport.setNumber(personDetails.getPassportNumber());
        } else {
            passport = null;
        }

        person.setId(personDetails.getId());
        person.setFirstName(personDetails.getFirstname());
        person.setLastName(personDetails.getLastname());
        person.setCountry(country);
        person.setAge(personDetails.getAge() == null ? 0 : personDetails.getAge());
        person.setPosition(personDetails.getPosition() == null ? "Web Designer" : personDetails.getPosition());
        person.setBoss(personDetails.getBoss() == null ? 167 : personDetails.getBoss());
        person.setUpdated(LocalDateTime.now());

        if(passport != null) passport.setPerson(person);

        person.setPassport(passport);

        return person;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final com.oz.demojar.dto.PersonDTO other = (com.oz.demojar.dto.PersonDTO) obj;
        return Objects.equals(this.id, other.getId());
    }

    public boolean isValid() {
        return this.id != null && this.firstname != null && this.lastname != null;
    }

    @Bean
    public static ModelMapper modelMapper() {
        return new ModelMapper();
    }
}

