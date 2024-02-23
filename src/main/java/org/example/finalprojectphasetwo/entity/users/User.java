package org.example.finalprojectphasetwo.entity.users;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

import jakarta.persistence.*;
import lombok.experimental.SuperBuilder;
import org.example.finalprojectphasetwo.entity.enumeration.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "users_table")
@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    String firstName;

    String lastName;

    String emailAddress;

    String username;

    String password;

    @Column(columnDefinition = "boolean default false")
    boolean isActive;

    @Column(columnDefinition = "boolean default false")
    boolean hasPermission;

    boolean isEnabled;

    LocalDate creationDate;

    @Enumerated(EnumType.STRING)
    Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}