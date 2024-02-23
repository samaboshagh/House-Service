package org.example.finalprojectphasetwo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

import jakarta.persistence.*;
import org.example.finalprojectphasetwo.entity.enumeration.OrderStatus;
import org.example.finalprojectphasetwo.entity.services.SubService;
import org.example.finalprojectphasetwo.entity.users.Customer;

import java.time.LocalDate;
import java.time.ZonedDateTime;
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

    LocalDate timeOfOrder;

    String address;

    ZonedDateTime OrderEndTime;

    @ManyToOne
    Customer customer;

    @ManyToOne
    SubService subService;

    @Enumerated(EnumType.STRING)
    OrderStatus status;

    @Column(columnDefinition = "boolean default false")
    boolean isPaid;

    @JsonIgnore
    @OneToMany(mappedBy = "order", cascade = CascadeType.MERGE)
    List<Suggestion> suggestions;

    @Override
    public String toString() {
        return "Order{" +
               "id=" + id +
               ", description='" + description + '\'' +
               ", suggestedPrice=" + suggestedPrice +
               ", timeOfOrder=" + timeOfOrder +
               ", address='" + address + '\'' +
               ", OrderEndTime=" + OrderEndTime +
               ", subService=" + subService +
               ", status=" + status +
               ", isPaid=" + isPaid +
               '}';
    }
}