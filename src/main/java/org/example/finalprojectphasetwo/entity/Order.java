package org.example.finalprojectphasetwo.entity;

import jakarta.validation.constraints.Future;
import lombok.*;
import lombok.experimental.FieldDefaults;

import jakarta.persistence.*;
import org.example.finalprojectphasetwo.entity.enumeration.OrderStatus;
import org.example.finalprojectphasetwo.entity.services.SubService;
import org.example.finalprojectphasetwo.entity.users.Customer;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "orders")
@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    String description;

    Double suggestedPrice;

    @Future
    LocalDate timeOfOrder;

    String address;

    @ManyToOne
    Customer customer;

    @ManyToOne
    SubService subService;

    @Enumerated(EnumType.STRING)
    OrderStatus status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.MERGE)
    List<Suggestion> suggestions;

}