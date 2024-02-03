package org.example.finalprojectphasetwo.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.finalprojectphasetwo.entity.users.Customer;


import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Builder
@ToString
@Entity
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    String cardNumber;

    Integer cvv2;

    LocalDate expirationDate;

    @ManyToOne(cascade = CascadeType.ALL)
    Customer customer;

}