package org.example.finalprojectphasetwo.dto;

import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.finalprojectphasetwo.entity.users.Customer;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class PayWithCardDto {

    @NonNull
    @Pattern(regexp = "^[0-9]{16}$", message = "INVALID CARD NUMBER")
    String cardNumber;

    @NonNull
    @Pattern(regexp = "^[0-9]{4}$", message = "INVALID CVV2 NUMBER")
    Integer cvv2;

    @NonNull
    LocalDate expirationDate;

    Customer customer;

}