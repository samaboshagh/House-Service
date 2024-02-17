package org.example.finalprojectphasetwo.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class AddAndDeleteSpecialistFromSubServiceRequest {

    @NotBlank(message = "SUB SERVICE TITLE IS REQUIRED")
    String subServiceTitle;

    @NotBlank(message = "SPECIALIST USERNAME IS REQUIRED")
    String specialistUsername;

}