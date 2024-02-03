package org.example.finalprojectphasetwo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.finalprojectphasetwo.entity.users.Specialist;

import java.time.LocalDate;

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

    LocalDate suggestionCreationDate = LocalDate.now();

    Double suggestedPrice;

    @Future
    LocalDate suggestedStartDate;

    LocalDate workDuration;

    @ManyToOne
    Specialist specialist;

    @ManyToOne(cascade = CascadeType.MERGE)
    Order order;

}