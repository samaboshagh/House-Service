package org.example.finalprojectphasetwo.dto.request;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class AddCommentDto {

    String comment;

    @Size(max = 5)
    Integer score;

    Integer orderId;

}