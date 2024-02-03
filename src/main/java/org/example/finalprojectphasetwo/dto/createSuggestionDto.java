package org.example.finalprojectphasetwo.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.finalprojectphasetwo.entity.users.Specialist;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class createSuggestionDto {

    @NonNull
    Double suggestedPrice;

    @NonNull
    LocalDate suggestedStartDate;

    @NonNull
    LocalDate workDuration;

    Specialist specialist;

}