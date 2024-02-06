package org.example.finalprojectphasetwo.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class CreateSuggestionDto {

    Double suggestedPrice;

    LocalDate suggestedStartDate;

    Integer workDuration;

    @NotBlank
    String specialistUsername;

    Integer orderId;

}