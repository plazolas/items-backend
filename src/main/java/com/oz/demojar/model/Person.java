package com.oz.demojar.model;
import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
//import javax.xml.validation.Validator;  // deprecated

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

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
    //@NotEmpty(message = "not empty")
    @Size(min = 2, message = "at least 2 chars")
    private String firstName;

    @JsonProperty("lastname")
    @Column(nullable = false)
    //@NotEmpty(message = "not empty")
    @Size(min = 2, message = "at least 2 chars")
    private String lastName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="country_id", referencedColumnName = "id")
    @Valid
    private Country country = new Country();

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "passport_id", referencedColumnName = "id")
    private Passport passport;

    public Person() {}

    public Person(String firstName, String lastName, Country country) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
    }

    public Person(String firstName, String lastName, Country country, Passport passport) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.passport = passport;
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
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Person{id=" + id + ", firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' +
                ", country=" + country + ", passport=" + passport + '}';
    }
}
