package org.example.finalprojectphasetwo.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import org.example.finalprojectphasetwo.entity.users.Specialist;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    String comment;

    Integer score;

    @OneToOne
    Order order;

    @ManyToOne
    Specialist specialist;

}