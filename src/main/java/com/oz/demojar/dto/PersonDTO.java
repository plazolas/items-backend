package com.oz.demojar.dto;

import java.io.Serializable;
import java.util.Objects;

import com.oz.demojar.model.Country;
import com.oz.demojar.model.Passport;
import com.oz.demojar.model.Person;
import lombok.*;

@Getter
@Setter
public class PersonDTO implements Serializable {

    private Long id;

    private String firstname;

    private String lastname;

    private String position;

    private Integer age;

    private Integer boss;

    private Country country = new Country();
    
    private Passport passport;

    public PersonDTO() {}

    public PersonDTO(String firstname, String lastname, Country country, String position, int age, int boss) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.country = country;
        this.position = position;
        this.age = age;
        this.boss = boss;
    }

    public PersonDTO(String firstname, String lastname, Country country, Passport passport) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.country = country;
        this.passport = passport;
    }

    public PersonDTO(String firstname, String lastname, Country country, Passport passport, String position) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.country = country;
        this.passport = passport;
        this.position = position;
    }

    public PersonDTO(String firstname, String lastname, Country country, Passport passport, String position, int age) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.country = country;
        this.passport = passport;
        this.position = position;
        this.age = age;
    }

    public PersonDTO(String firstname, String lastname, Country country, Passport passport, String position, int age, int boss) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.country = country;
        this.passport = passport;
        this.position = position;
        this.age = age;
        this.boss = boss;
    }

    public PersonDTO(Person person) {
        this.firstname = person.getFirstName();
        this.lastname = person.getLastName();
        this.country = person.getCountry();
        this.passport = person.getPassport();
        this.position = person.getPosition();
        this.age = person.getAge();
        this.boss = person.getBoss();
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

    @Override
    public String toString() {
        return "PersonDTO {id=" + id + ", firstname='" + firstname + '\'' + ", lastname='" + lastname + '\'' +
                ", country=" + country + ", passport=" + passport + ", position=" + position + ", age=" + age + ", boss=" + boss +'}';
    }
}

