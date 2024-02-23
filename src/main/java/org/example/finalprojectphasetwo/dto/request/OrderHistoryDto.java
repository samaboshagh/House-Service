package org.example.finalprojectphasetwo.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.finalprojectphasetwo.entity.enumeration.OrderStatus;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@Getter
public class OrderHistoryDto {

    LocalDate startDate;

    LocalDate endDate;

    OrderStatus status;

    String mainServiceTitle;

    String subServiceTitle;
}