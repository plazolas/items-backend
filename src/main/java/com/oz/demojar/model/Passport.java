package com.oz.demojar.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;;
import java.time.LocalDate;

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

    public Passport(String number, LocalDate expDate, Person person) {
        this.number = number;
        this.expDate = expDate;
        this.person = person;
        this.country = person.getCountry();
    }

}