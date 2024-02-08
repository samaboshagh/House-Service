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

    @NotBlank
    String specialistUsername;

    Integer orderId;

}