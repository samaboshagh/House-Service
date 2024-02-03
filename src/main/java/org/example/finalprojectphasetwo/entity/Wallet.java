package org.example.finalprojectphasetwo.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Wallet{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id ;

    Double creditAmount = 0.0;

}