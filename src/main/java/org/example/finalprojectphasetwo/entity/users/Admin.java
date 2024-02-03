package org.example.finalprojectphasetwo.entity.users;

import lombok.*;

import jakarta.persistence.*;
import lombok.experimental.SuperBuilder;


@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@Entity
public class Admin extends User {

}