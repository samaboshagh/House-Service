package org.example.finalprojectphasetwo.entity.users;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

import jakarta.persistence.*;
import lombok.experimental.SuperBuilder;
import org.example.finalprojectphasetwo.entity.enumeration.Role;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "users_table")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    String firstName;

    String lastName;

    @Column(unique = true)
    String emailAddress;

    @Column(unique = true)
    String username;

    String password;

    @Column(columnDefinition = "boolean default false")
    boolean isActive;

    @Column(columnDefinition = "boolean default false")
    boolean hasPermission;

    LocalDate creationDate;

    @Enumerated(EnumType.STRING)
    Role role;

    @Override
    public String toString() {
        return "UserCaptcha{" +
               "UserCaptcha id = " + getId() +
               " firstName =' " + firstName + '\'' +
               ", lastName =' " + lastName + '\'' +
               '}';
    }
}