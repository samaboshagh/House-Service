package org.example.finalprojectphasetwo.service.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@Getter
@Setter
public class SpecialistSingUpDto extends UserSingUpDto {

    byte[] profileImage;

    String pathName;

}