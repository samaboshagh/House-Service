package org.example.finalprojectphasetwo.entity.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;

import jakarta.persistence.*;
import org.example.finalprojectphasetwo.entity.Suggestion;
import org.example.finalprojectphasetwo.entity.Wallet;
import org.example.finalprojectphasetwo.entity.enumeration.SpecialistStatus;

import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Specialist extends User {

    @Enumerated(EnumType.STRING)
    SpecialistStatus specialistStatus;

    @Min(0)
    @Max(5)
    Integer star = 0;

    @Lob
    byte[] profileImage;

    @OneToOne
    Wallet wallet;

    @OneToMany(mappedBy = "specialist", cascade = CascadeType.ALL)
    List<Suggestion> suggestions;


    public Specialist(
            @Pattern(regexp = "^[A-Za-z]+$") String firstName
            , @Pattern(regexp = "^[A-Za-z]+$") String lastName
            , @Email String emailAddress, String username
            , @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8}$") String password
            , Integer star
    ) {
        super(firstName, lastName, emailAddress, username, password);
        this.star = star;
    }

    public Specialist(String firstName,
                      String lastName,
                      String emailAddress,
                      String username,
                      String password,
                      SpecialistStatus specialistStatus) {
        super(firstName, lastName, emailAddress, username, password);
        this.specialistStatus = specialistStatus;
    }
}