package org.example.finalprojectphasetwo.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.finalprojectphasetwo.entity.users.Specialist;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class CreateSuggestionDto {

    Double suggestedPrice;

    LocalDate suggestedStartDate;

    String workDuration;

    Specialist specialist;

}