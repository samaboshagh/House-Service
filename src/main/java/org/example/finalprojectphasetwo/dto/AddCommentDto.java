package org.example.finalprojectphasetwo.dto;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.finalprojectphasetwo.entity.Order;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class AddCommentDto {

    String comment;

    @Size(max = 5)
    @NonNull
    Integer score;

    Order order;

}