package com.trivadis.dataplatform.privacy.persistence;

import lombok.*;

@Data
@ToString
@EqualsAndHashCode
@Builder
@AllArgsConstructor
public class PseudonymizerResultDto {
    String identifier;
    String pseudonym;
}
