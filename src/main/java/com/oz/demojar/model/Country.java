package com.oz.demojar.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@JsonSerialize
@Entity
@Table(name="country")
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

    public Country (String name) {
        this.name = name;
    }
    public Country (Long id, String name) {
        this.name = name;
        this.id = id;
    }

    @Override
    public String toString() { return "Country{ id=" + id + ", name='" + name + "' }"; }
}
