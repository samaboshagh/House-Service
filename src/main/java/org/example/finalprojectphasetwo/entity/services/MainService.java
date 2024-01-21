package org.example.finalprojectphasetwo.entity.services;

import lombok.*;
import lombok.experimental.FieldDefaults;

import jakarta.persistence.*;


import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "main_service")
@Entity
public class MainService  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id ;

    String title;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mainService")
    Set<SubService> subServices;

    @Override
    public String toString() {
        return " MainService{" +
               " MainService id = " + getId() + " " +
               " title =' " + title +
               '}';
    }
}