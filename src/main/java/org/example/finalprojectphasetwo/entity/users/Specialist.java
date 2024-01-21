package org.example.finalprojectphasetwo.entity.users;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.FieldDefaults;

import jakarta.persistence.*;
import org.example.finalprojectphasetwo.entity.Wallet;
import org.example.finalprojectphasetwo.entity.enumeration.SpecialistStatus;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Specialist extends User {

    @Enumerated(EnumType.STRING)
    @Column(name = "specialist_status")
    SpecialistStatus specialistStatus;

    @Min(0)
    @Max(5)
    Double star = 0.0;

    @Lob
    @Column(name = "profile_image")
    byte[] profileImage;

    @OneToOne
    Wallet wallet;

}