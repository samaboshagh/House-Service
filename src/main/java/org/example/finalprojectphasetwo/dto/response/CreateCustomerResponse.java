package org.example.finalprojectphasetwo.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class CreateCustomerResponse {

    String firstName;

    String lastName;

    String emailAddress;

    String username;

}