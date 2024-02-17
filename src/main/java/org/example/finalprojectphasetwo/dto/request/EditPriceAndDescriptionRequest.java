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

    @NotBlank(message = "SUB SERVICE TITLE IS REQUIRED")
    String subServiceTitle;

    Double basePrice;

    @NotBlank(message = "DESCRIPTION IS REQUIRED")
    String description;

}