package org.example.finalprojectphasetwo.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@Getter
public class SpecialistSingUpDto extends UserSingUpDto {

    byte[] profileImage;

    String pathName;

}