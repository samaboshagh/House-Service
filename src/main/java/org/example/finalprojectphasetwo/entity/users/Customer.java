package org.example.finalprojectphasetwo.entity.users;

import jakarta.persistence.CascadeType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import org.example.finalprojectphasetwo.entity.Order;
import org.example.finalprojectphasetwo.entity.Wallet;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Customer extends User {

    @OneToMany(mappedBy = "customer", cascade = CascadeType.MERGE)
    List<Order> orders;

    @OneToOne
    Wallet wallet;

    public Customer(String firstName, String lastName, String emailAddress, String username, String password) {
        super(firstName, lastName, emailAddress, username, password);
    }

    public Customer(String firstName, String lastName, String emailAddress, String username, String password, List<Order> orders) {
        super(firstName, lastName, emailAddress, username, password);
        this.orders = orders;
    }
}