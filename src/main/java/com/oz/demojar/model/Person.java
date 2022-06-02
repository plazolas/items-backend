package com.oz.demojar.model;
import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Objects;

import com.oz.demojar.config.AppProperties;
import com.oz.demojar.dto.PersonDTO;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonSerialize
@Builder
@Entity
@Table(name="person")
public class Person {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @JsonProperty("firstname")
    @Column(nullable = false)
    // @NotEmpty(message = "not empty")
    @Size(min = 2, message = "at least 2 chars")
    private String firstName;

    @JsonProperty("lastname")
    @Column(nullable = false)
    @Size(min = 2, message = "at least 2 chars")
    private String lastName;

    @JsonProperty("position")
    @Column
    private String position;

    @JsonProperty("age")
    @Column
    private Integer age;

    @JsonProperty("boss")
    @Column
    private Integer boss;

    @LastModifiedDate
    @Column(name = "updated")
    private LocalDateTime updated;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="country_id", referencedColumnName = "id")
    @Valid
    private Country country = new Country();

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "passport_id", referencedColumnName = "id")
    private Passport passport;

    public Person(String firstName, String lastName, Country country, String position, int age, int boss) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.position = position;
        this.age = age;
        this.boss = boss;
    }

    public Person(String firstName, String lastName, Country country, Passport passport) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.passport = passport;
    }

    public Person(String firstName, String lastName, Country country, Passport passport, String position) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.passport = passport;
        this.position = position;
    }

    public Person(String firstName, String lastName, Country country, Passport passport, String position, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.passport = passport;
        this.position = position;
        this.age = age;
    }

    public Person(String firstName, String lastName, Country country, Passport passport, String position, int age, int boss) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.passport = passport;
        this.position = position;
        this.age = age;
        this.boss = boss;
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
        final Person other = (Person) obj;
        return Objects.equals(this.id, other.id);
    }

    public boolean isValid() {
        return this.id != null && this.firstName != null && this.lastName != null;
    }

    public PersonDTO convertToDTO() {

        PersonDTO personDTO = new PersonDTO();

        personDTO.setId(this.getId());
        personDTO.setFirstname(this.getFirstName());
        personDTO.setLastname(this.getLastName());
        personDTO.setPassport(this.getPassport());
        personDTO.setCountry(this.getCountry());
        personDTO.setAge(this.getAge() == null ? 0 : this.getAge());
        personDTO.setPosition(this.getPosition() == null ? "Slave" : this.getPosition());
        personDTO.setBoss(this.getBoss() == null ? 167 : this.getBoss());

        this.setUpdated(LocalDateTime.now());

        return personDTO;
    }

}
