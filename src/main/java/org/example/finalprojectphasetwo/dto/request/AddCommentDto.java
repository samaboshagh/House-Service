package org.example.finalprojectphasetwo.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class AddCommentDto {

    String comment;

    Integer score;

    Integer suggestionId;

}