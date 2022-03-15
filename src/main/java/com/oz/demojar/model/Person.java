package com.oz.demojar.model;
import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name="person")
@Getter
@Setter
@JsonSerialize
public class Person implements Serializable {

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
    @Column(nullable = true)
    private String position;

    @JsonProperty("age")
    @Column(nullable = true)
    private Integer age;

    @JsonProperty("boss")
    @Column(nullable = true)
    private Integer boss;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="country_id", referencedColumnName = "id")
    @Valid
    private Country country = new Country();

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "passport_id", referencedColumnName = "id")
    private Passport passport;

    public Person() {}

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

    @Override
    public String toString() {
        return "Person{id=" + id + ", firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' +
                ", country=" + country + ", passport=" + passport + ", position=" + position + ", age=" + age + '}';
    }
}
