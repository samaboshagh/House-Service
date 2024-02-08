package org.example.finalprojectphasetwo.entity.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;

import jakarta.persistence.*;
import lombok.experimental.SuperBuilder;
import org.example.finalprojectphasetwo.entity.Comment;
import org.example.finalprojectphasetwo.entity.Suggestion;
import org.example.finalprojectphasetwo.entity.Wallet;
import org.example.finalprojectphasetwo.entity.enumeration.SpecialistStatus;

import java.util.List;


@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Specialist extends User {

    @Enumerated(EnumType.STRING)
    SpecialistStatus specialistStatus;

    String specialization;

    Integer star;

    @Lob
    @JsonIgnore
    byte[] profileImage;

    @OneToOne
    Wallet wallet;

    @JsonIgnore
    @OneToMany(mappedBy = "specialist", cascade = CascadeType.ALL)
    List<Suggestion> suggestions;

    @JsonIgnore
    @OneToMany(mappedBy = "specialist", cascade = CascadeType.MERGE)
    List<Comment> comments;

}