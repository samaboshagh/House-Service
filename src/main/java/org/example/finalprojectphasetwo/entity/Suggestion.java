package org.example.finalprojectphasetwo.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.finalprojectphasetwo.entity.users.Specialist;

import java.time.LocalDate;
import java.time.ZonedDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@Entity
public class Suggestion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    LocalDate suggestionCreationDate;

    Double suggestedPrice;

    ZonedDateTime suggestedStartDate;

    Integer workDuration;

    @ManyToOne
    Specialist specialist;

    @ManyToOne(cascade = CascadeType.MERGE)
    Order order;

}