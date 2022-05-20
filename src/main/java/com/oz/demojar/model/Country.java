package com.oz.demojar.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name="country")
@Getter
@Setter
@JsonSerialize
public class Country {

    //private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    //private Validator validator = factory.getValidator();

    @Id
    @NotNull
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotEmpty
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "country", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private List<Person> persons = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "country", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private List<Passport> passports = new ArrayList<>();

    public Country () {}

    public Country (String name) {
        this.name = name;
    }

    @Override
    public String toString() { return "Country{id=" + id + ", name='" + name + "'}"; }
}
