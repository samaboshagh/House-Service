package org.example.finalprojectphasetwo.dto;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class CommentDtoTOShowToSpecialist {

    @Size(max = 5)
    Integer score;

}