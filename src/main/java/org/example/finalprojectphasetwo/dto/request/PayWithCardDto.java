package org.example.finalprojectphasetwo.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class PayWithCardDto {

    @Pattern(regexp = "^[0-9]{16}$", message = "INVALID CARD NUMBER")
    String cardNumber;

    @Pattern(regexp = "^[0-9]{4}$", message = "INVALID CVV2 NUMBER")
    Integer cvv2;

    LocalDate expirationDate;

    Integer orderId;

    Integer suggestionId;

}