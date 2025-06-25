package com.oz.demojar.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

import com.oz.demojar.config.AppProperties;
import com.oz.demojar.model.Country;
import com.oz.demojar.model.Passport;
import com.oz.demojar.model.Person;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
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

    public PersonDTO(String firstname, String lastname, String countryName, String position, int age, int boss) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.countryName = countryName;
        this.position = position;
        this.age = age;
        this.boss = boss;
    }

    public PersonDTO(String firstname, String lastname, String countryName, String passportNumber) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.countryName = countryName;
        this.passportNumber = passportNumber;
    }

    public PersonDTO(Person person) {
        this.firstname = person.getFirstName();
        this.lastname = person.getLastName();
        this.countryName = person.getCountry().getName();
        this.passportId = person.getPassport().getId().intValue();
        this.passportNumber = person.getPassport().getNumber();
        this.position = person.getPosition();
        this.age = person.getAge();
        this.boss = person.getBoss();
    }

    public Person convertToEntity(PersonDTO personDetails) throws NoSuchElementException {

        Person person = new Person();
        Country country = new Country(personDetails.getCountryId().longValue(), personDetails.getCountryName());

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

