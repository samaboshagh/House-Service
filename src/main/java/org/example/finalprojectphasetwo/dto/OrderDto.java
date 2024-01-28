package org.example.finalprojectphasetwo.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.finalprojectphasetwo.entity.Suggestion;
import org.example.finalprojectphasetwo.entity.enumeration.OrderStatus;
import org.example.finalprojectphasetwo.entity.services.SubService;
import org.example.finalprojectphasetwo.entity.users.Customer;

import java.time.LocalDate;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@Getter
public class OrderDto {

    String description;

    Double suggestedPrice;

    LocalDate timeOfOrder;

    String address;

    Customer customer;

    SubService subService;

    OrderStatus status;

    Suggestion suggestion;

}