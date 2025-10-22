package com.oz.demojar.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonSerialize
@Entity
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

}
