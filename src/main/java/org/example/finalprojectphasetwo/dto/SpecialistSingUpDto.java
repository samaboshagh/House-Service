package org.example.finalprojectphasetwo.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@Getter
public class SpecialistSingUpDto extends UserSingUpDto {

    @NonNull
    String specialization;

    byte[] profileImage;

    String pathName;

}