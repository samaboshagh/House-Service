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
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "users_table")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Pattern(regexp = "^[A-Za-z]+$")
    String firstName;

    @Pattern(regexp = "^[A-Za-z]+$")
    String lastName;

    @Column(unique = true)
    @Email
    String emailAddress;

//    @Column(unique = true , nullable = false)
    String username;

    @Column(nullable = false)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8}$")
    String password;

    @Column(columnDefinition = "boolean default false")
    boolean isActive;

    @Column(columnDefinition = "boolean default false")
    boolean hasPermission;

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