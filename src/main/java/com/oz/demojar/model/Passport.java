package com.oz.demojar.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name="passport")
@Getter
@Setter
@JsonSerialize
public class Passport implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    // @Min(5) @Max(15)
    @Size(min = 4, max=15, message = "at least 2 chars")
    @NotBlank(message = "Passport number is mandatory")
    @Column(unique = true, nullable = false)
    private String number;

    @Future(message = "Passport date should be in the future")
    private LocalDate expDate;

    @JsonIgnore
    @OneToOne(mappedBy = "passport", cascade = CascadeType.MERGE)
    private Person person;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "country_id", referencedColumnName = "id")
    private Country country;

    public Passport(){}

    public Passport(String number, LocalDate expDate, Person person) {
        this.number = number;
        this.expDate = expDate;
        this.person = person;
        this.country = person.getCountry();
    }

    @Override
    public String toString() {
        return "Passport{id=" + id + ", number='" + number + '\'' + ", expDate=" + expDate  + '}';
    }
}