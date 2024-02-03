package org.example.finalprojectphasetwo.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.finalprojectphasetwo.entity.services.MainService;

@Getter
@Setter
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class subServiceDto {

    @NonNull
    String subServiceTitle;

    @NonNull
    Double basePrice;

    String description;

    MainService mainService;

}