package org.example.finalprojectphasetwo.entity.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.experimental.SuperBuilder;
import org.example.finalprojectphasetwo.entity.Card;
import org.example.finalprojectphasetwo.entity.Order;
import org.example.finalprojectphasetwo.entity.Wallet;

import java.util.List;


@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Customer extends User {

    @JsonIgnore
    @OneToMany(mappedBy = "customer", cascade = CascadeType.MERGE)
    List<Order> orders;

    @OneToOne
    Wallet wallet;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.MERGE)
    List<Card> card;

    public Customer(String firstName, String lastName, String emailAddress, String username, String password) {
        super(firstName, lastName, emailAddress, username, password);
    }

    public Customer(String firstName, String lastName, String emailAddress, String username, String password, List<Order> orders) {
        super(firstName, lastName, emailAddress, username, password);
        this.orders = orders;
    }
}