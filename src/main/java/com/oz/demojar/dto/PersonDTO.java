package com.oz.demojar.dto;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.oz.demojar.model.Country;
import com.oz.demojar.model.Passport;
import com.oz.demojar.model.Person;
import com.oz.demojar.service.PersonService;
import lombok.*;
import org.hibernate.internal.util.ZonedDateTimeComparator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO implements Serializable {

    @Autowired
    private static Environment env;

    private Long id;

    private String firstname;

    private String lastname;

    private String position;

    private Integer age;

    private Integer boss;

    private Country country = new Country();

    private Passport passport;

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

    public String DateToString(LocalDateTime localDateTime) {
        DateTimeFormatter dateTimeFormatter =
                DateTimeFormatter.ofPattern(Objects.requireNonNull(env.getProperty("spring.datasource.datetime")));
        String timezone = Objects.requireNonNull(env.getProperty("spring.datasource.datetime"));

        if (localDateTime == null) localDateTime = LocalDateTime.now();

        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, ZoneId.of(timezone));
        DateTimeFormatter formatter = dateTimeFormatter;
        return  zonedDateTime.format(formatter);
    }

    public static String DateToString() {
        DateTimeFormatter dateTimeFormatter =
                DateTimeFormatter.ofPattern(Objects.requireNonNull(env.getProperty("spring.datasource.datetime")));
        String timezone = Objects.requireNonNull(env.getProperty("spring.datasource.datetime"));

        LocalDateTime localDateTime = LocalDateTime.now();

        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, ZoneId.of(timezone));
        DateTimeFormatter formatter = dateTimeFormatter;
        return  zonedDateTime.format(formatter);
    }

//    public void setSubmissionDate(Date date, String timezone) {
//        dateFormat.setTimeZone(TimeZone.getTimeZone(timezone));
//        this.updated = dateFormat.format(date);
//    }

    public static Person convertToEntity(PersonDTO personDetails) throws NoSuchElementException {

        Person person = new Person();

        person.setId(personDetails.getId());
        person.setPassport(personDetails.getPassport());
        person.setCountry(personDetails.getCountry());
        person.setFirstName(personDetails.getFirstname());
        person.setLastName(personDetails.getLastname());
        person.setAge(personDetails.getAge() == null ? 0 : personDetails.getAge());
        person.setPosition(personDetails.getPosition() == null ? "Secretary" : personDetails.getPosition());
        person.setBoss(personDetails.getBoss() == null ? 167 : personDetails.getBoss());

        // System.out.println(DateToString());
        person.setUpdated(LocalDateTime.now());

        System.out.println("person:");
        System.out.println(person);

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

    @Override
    public String toString() {
        return "PersonDTO {id=" + id + ", firstname='" + firstname + '\'' + ", lastname='" + lastname + '\'' +
                ", country=" + country + ", passport=" + passport + ", position=" + position + ", age=" + age + ", boss=" + boss + '}';
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}

