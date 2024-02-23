package org.example.finalprojectphasetwo.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class ChangePasswordRequest {

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8}$",
            message = "YOUR PASSWORD MUST CONTAIN AT LEAST 1 LETTER, 1 NUMBER, AND BE 8 CHARACTERS LONG")
    @NotBlank(message = "PASSWORD IS REQUIRED")
    String password;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8}$",
            message = "YOUR PASSWORD MUST CONTAIN AT LEAST 1 LETTER, 1 NUMBER, AND BE 8 CHARACTERS LONG")
    @NotBlank(message = "CONFIRM PASSWORD IS REQUIRED")
    String confirmPassword;

}