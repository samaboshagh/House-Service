package org.example.finalprojectphasetwo.service.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.finalprojectphasetwo.entity.services.MainService;

@Getter
@Setter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class SubServiceDto {

    String subServiceTitle;

    Double basePrice;

    String description;

    MainService mainService;

}