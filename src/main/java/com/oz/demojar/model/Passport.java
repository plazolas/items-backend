package com.oz.demojar.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Locale;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonSerialize
@Builder
@Entity
@Table(name="passport")
public class Passport {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Min(5) @Max(15)
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

    public Passport(Long id, String number, Person person, Country country) {
        this.id = id;
        this.number = number;
        this.expDate = LocalDate.now().plusYears(5L);
        this.person  = person;
        this.country = country;
    }
    public Passport(String number, LocalDate expDate, Person person) {
        this.number = number;
        this.expDate = expDate;
        this.person = person;
        this.country = person.getCountry();
    }

    public Passport(LocalDate expDate, Person person, Country country) {
        int rand = (int) (Math.random() * 100000) + 10000;
        String randStr = String.valueOf(rand);
        String passportNumber = (randStr.length() < 6) ? randStr + "0" : randStr;

        this.number = country.getName().toUpperCase(Locale.ROOT).substring(0,2) +
                person.getFirstName().toUpperCase(Locale.ROOT).substring(0,2) +
                person.getLastName().toUpperCase(Locale.ROOT).substring(0,3) +
                passportNumber;
        this.expDate = expDate;
        this.person = person;
        this.country = person.getCountry();
    }

}