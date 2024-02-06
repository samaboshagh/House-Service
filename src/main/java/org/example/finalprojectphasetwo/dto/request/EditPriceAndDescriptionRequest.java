package org.example.finalprojectphasetwo.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class EditPriceAndDescriptionRequest {

    @NotBlank
    String subServiceTitle;

    Double basePrice;

    @NotBlank
    String description;

}