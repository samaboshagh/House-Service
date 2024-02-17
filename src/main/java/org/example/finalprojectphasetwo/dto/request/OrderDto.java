package org.example.finalprojectphasetwo.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@Getter
public class OrderDto {

    @NotBlank(message = "DESCRIPTION IS REQUIRED")
    String description;

    Double suggestedPrice;

    LocalDate timeOfOrder;

    @NotBlank(message = "ADDRESS IS REQUIRED")
    String address;

    @NotBlank(message = "CUSTOMER USERNAME IS REQUIRED")
    String customerUsername;

    @NotBlank(message = "SUB SERVICE TITLE IS REQUIRED")
    String subServiceTitle;

}