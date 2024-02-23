package org.example.finalprojectphasetwo.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@ToString
public class ReportDto {

    LocalDate creationDate;

    Long requestOfOrders;

    Long doneOrders;
}