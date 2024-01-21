package org.example.finalprojectphasetwo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.finalprojectphasetwo.entity.users.Specialist;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@Entity
public class Suggestion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "suggestion_creation_date")
    LocalDate suggestionCreationDate = LocalDate.now();

    @Column(name = "suggested_price")
    Double suggestedPrice;

    @Column(name = "suggested_start_date")
    @Future
    LocalDate suggestedStartDate;

    @Column(name = "work_duration")
    String workDuration;

    @OneToOne
    Specialist specialist;

}