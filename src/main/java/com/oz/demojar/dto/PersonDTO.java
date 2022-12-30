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

    public Person convertToEntity(PersonDTO personDTO) throws NoSuchElementException {

        Person person = new Person();
        Country country = new Country();
        Passport passport = new Passport();

        if(personDTO.getCountryId() > 0 && personDTO.getCountryName().length() > 5) {
            country.setId(personDTO.getCountryId().longValue());
            country.setName(personDTO.getCountryName());
        } else {
            country.setId(50L);
            country.setName("United States");
        }

        if(personDTO.getPassportId() != null && personDTO.getPassportNumber() != null &&
                personDTO.getPassportId() > 0 && personDTO.getPassportNumber().length() > 4) {
            passport.setId(personDTO.getPassportId().longValue());
            passport.setNumber(personDTO.getPassportNumber());
        } else {
            passport = null;
        }

        person.setId(personDTO.getId());
        person.setFirstName(personDTO.getFirstname());
        person.setLastName(personDTO.getLastname());
        person.setCountry(country);
        person.setAge(personDTO.getAge() == null ? 0 : personDTO.getAge());
        person.setPosition(personDTO.getPosition() == null ? "Web Designer" : personDTO.getPosition());
        person.setBoss(personDTO.getBoss() == null ? 167 : personDTO.getBoss());
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

