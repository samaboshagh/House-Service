package org.example.finalprojectphasetwo.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class UserSingUpDto {

    @Pattern(regexp = "^[A-Za-z]+$")
    @NotBlank(message = "FIRSTNAME IS REQUIRED")
    String firstName;

    @Pattern(regexp = "^[A-Za-z]+$")
    @NotBlank(message = "LASTNAME IS REQUIRED")
    String lastName;

    @NotBlank(message = "EMAIL ADDRESS IS REQUIRED")
    @Email(message = "INVALID EMAIL")
    String emailAddress;

    @NotBlank(message = "USERNAME IS REQUIRED")
    String username;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8}$",
            message = "YOUR PASSWORD MUST CONTAIN AT LEAST 1 LETTER, 1 NUMBER, AND BE 8 CHARACTERS LONG")
    @NotBlank(message = "PASSWORD IS REQUIRED")
    String password;

    LocalDate creationDate = LocalDate.now();

    boolean isActive;

    boolean hasPermission;

}