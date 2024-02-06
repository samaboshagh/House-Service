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

    @NotBlank
    String subServiceTitle;

    Double basePrice;

    String description;

    @NotBlank
    String mainServiceName;

}