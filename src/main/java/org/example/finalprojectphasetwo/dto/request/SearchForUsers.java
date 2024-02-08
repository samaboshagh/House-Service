package org.example.finalprojectphasetwo.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.finalprojectphasetwo.entity.enumeration.Role;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class SearchForUsers {

    String firstName;

    String lastName;

    String emailAddress;

    String specialization;

    Integer star;

    String username;

    Role role;

}