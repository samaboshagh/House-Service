package org.example.finalprojectphasetwo.entity.services;

import lombok.*;
import lombok.experimental.FieldDefaults;

import jakarta.persistence.*;
import org.example.finalprojectphasetwo.entity.Order;
import org.example.finalprojectphasetwo.entity.users.Specialist;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@Table(name = "sub_service")
@Entity
public class SubService {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    String subServiceTitle;

    Double basePrice;

    String description;

    @ManyToOne
    MainService mainService;

    @OneToMany(mappedBy = "subService", cascade = CascadeType.MERGE)
    List<Order> order;

    @ManyToMany
    Set<Specialist> specialists;

    @Override
    public String toString() {
        return "SubService{" +
               " SubService id = " + getId() +
               ", subServiceTitle =' " + subServiceTitle + '\'' +
               ", mainService =" + mainService +
               '}';
    }
}