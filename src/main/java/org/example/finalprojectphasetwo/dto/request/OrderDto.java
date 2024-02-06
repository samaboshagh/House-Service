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

    String description;

    Double suggestedPrice;

    LocalDate timeOfOrder;

    @NotBlank
    String address;

    @NotBlank
    String customerUsername;

    @NotBlank
    String subServiceTitle;

}