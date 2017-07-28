package gov.samhsa.c2s.masteruiapi.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TelecomDto {
    private String system;

    private String value;

    private String use;
}
