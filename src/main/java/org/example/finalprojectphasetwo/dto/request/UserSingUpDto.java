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
    String firstName;

    @Pattern(regexp = "^[A-Za-z]+$")
    String lastName;

    @NonNull
    @Email(message = "INVALID EMAIL")
    String emailAddress;

    @NotBlank
    String username;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8}$",
            message = "INVALID PASSWORD")
    String password;

    LocalDate creationDate = LocalDate.now();

    boolean isActive;

    boolean hasPermission;
}