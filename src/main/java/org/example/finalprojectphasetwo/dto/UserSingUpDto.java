package org.example.finalprojectphasetwo.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.finalprojectphasetwo.entity.Wallet;

@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class UserSingUpDto {

    @Pattern(regexp = "^[A-Za-z]+$")
    String firstName;

    @Pattern(regexp = "^[A-Za-z]+$")
    String lastName;

    @NonNull
    @Email(message = "INVALID EMAIL")
    String emailAddress;

    @NonNull
    String username;

    @Column(nullable = false)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8}$",
            message = "INVALID PASSWORD")
    String password;

    Wallet wallet;

    boolean isActive;

    boolean hasPermission;

}