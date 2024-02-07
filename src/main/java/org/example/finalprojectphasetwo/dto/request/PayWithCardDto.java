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

    String cardNumber;

    Integer cvv2;

    LocalDate expirationDate;

    Integer orderId;

    Integer suggestionId;

}