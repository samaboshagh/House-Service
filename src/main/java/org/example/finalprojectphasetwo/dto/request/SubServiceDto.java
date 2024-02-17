package org.example.finalprojectphasetwo.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class SubServiceDto {

    @NotBlank(message = "SUB SERVICE TITLE IS REQUIRED")
    String subServiceTitle;

    Double basePrice;

    @NotBlank(message = "DESCRIPTION IS REQUIRED")
    String description;

    @NotBlank(message = "MAIN SERVICE NAME IS REQUIRED")
    String mainServiceName;

}