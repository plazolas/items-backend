package com.oz.demojar.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.context.annotation.Bean;
import org.springframework.lang.Nullable;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonSerialize
@Builder
@Entity
@Table(name="user")
public class User  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonProperty("username")
    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @JsonProperty("password")
    @Column(name = "password", nullable = false)
    private String password;

    @JsonProperty("active")
    private boolean active;

    @JsonProperty("roles")
    private String roles;

    @JsonProperty("phone")
    @Column(name = "phone", nullable = false)
    @ColumnDefault("000")
    private String phone;

    @JsonProperty("useremail")
    @Column(name = "useremail", nullable = true)
    private String useremail;

    public User(String username, String password, String roles) {
        setId(0L);
        setUsername(username);
        setPassword(password);
        setPhone("000");
        setUseremail("me@email.com");
        setActive(true);
        setRoles(roles);
    }
    public User(String username, String password, boolean active, String roles) {
        setId(0L);
        setUsername(username);
        setPassword(password);
        setPhone("000");
        setUseremail("me@email.com");
        setActive(active);
        setRoles(roles);
    }

    public String getEncryptPassword(String password) {
        BCryptPasswordEncoder encoder = bCryptPasswordEncoder(); // default strength=10
        return encoder.encode(password);
    }

    public boolean matchPassword(String rawPassword) {
        BCryptPasswordEncoder encoder = bCryptPasswordEncoder(); // default strength=10
        return bCryptPasswordEncoder().matches(rawPassword, this.getPassword());
    }

    @Override
    public String toString() {
        return "User {id=" + id + ", username=" + this.getUsername() + ", password=" +
                this.getPassword() + ",active=" + this.isActive() + ",roles="+ this.getRoles() +"}";
    }

    public static BCryptPasswordEncoder  bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2A);
    }

    public static BCrypt bCrypt() {
        return new BCrypt();
    }
}
