package org.example.finalprojectphasetwo.entity.users;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;

import jakarta.persistence.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@MappedSuperclass
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "first_name")
    @Pattern(regexp = "^[A-Za-z]+$")
    String firstName;

    @Column(name = "last_name")
    @Pattern(regexp = "^[A-Za-z]+$")
    String lastName;

    @Column(name = "email_address", unique = true)
    @Email
    String emailAddress;

    String username;

    @Column(nullable = false)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8}$")
    String password;

    @Column(name = "is_active", columnDefinition = "boolean default false")
    boolean isActive;

    @Column(name = "has_permission", columnDefinition = "boolean default false")
    boolean hasPermission;


    @Column(name = "creation_date")
    LocalDate creationDate = LocalDate.now();

    @Override
    public String toString() {
        return "User{" +
               "User id = " + getId() +
               " firstName =' " + firstName + '\'' +
               ", lastName =' " + lastName + '\'' +
               '}';
    }
}