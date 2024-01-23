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

    Long totalAmount = 0L;

    Long cashAmount = 0L;

    Long creditAmount = 0L;

}