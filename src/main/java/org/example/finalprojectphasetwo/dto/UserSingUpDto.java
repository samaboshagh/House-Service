package org.example.finalprojectphasetwo.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.finalprojectphasetwo.entity.Wallet;

@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class UserSingUpDto {

    String firstName;

    String lastName;

    String emailAddress;

    String username;

    String password;

    Wallet wallet;

    boolean isActive;

    boolean hasPermission;

}