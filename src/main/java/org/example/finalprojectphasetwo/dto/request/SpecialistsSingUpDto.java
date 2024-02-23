package org.example.finalprojectphasetwo.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@Getter
public class SpecialistsSingUpDto extends UserSingUpDto {

    @NotBlank(message = "SPECIALIZATION IS REQUIRED")
    String specialization;

    byte[] profileImage;

    String pathName;

}