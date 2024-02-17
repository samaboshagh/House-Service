package org.example.finalprojectphasetwo.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.ZonedDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class CreateSuggestionDto {

    Double suggestedPrice;

    ZonedDateTime suggestedStartDate;

    Integer workDuration;

    @NotBlank(message = "SPECIALIST USERNAME IS REQUIRED")
    String specialistUsername;

    Integer orderId;

}