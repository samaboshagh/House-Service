package org.example.finalprojectphasetwo.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Wallet{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id ;

    @Column(name = "total_amount")
    Long totalAmount = 0L;

    @Column(name = "cash_amount")
    Long cashAmount = 0L;

    @Column(name = "credit_amount")
    Long creditAmount = 0L;

}