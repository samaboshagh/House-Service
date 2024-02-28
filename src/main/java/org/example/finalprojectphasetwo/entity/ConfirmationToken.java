package org.example.finalprojectphasetwo.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.finalprojectphasetwo.entity.users.User;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Entity
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer tokenId;

    String confirmationToken;

    @Temporal(TemporalType.TIMESTAMP)
    Date createdDate;

    boolean isActive;

    @OneToOne
    User user;

    public ConfirmationToken(User user,boolean isActive) {
        this.user = user;
        this.isActive = isActive;
        createdDate = new Date();
        confirmationToken = UUID.randomUUID().toString();
    }
}