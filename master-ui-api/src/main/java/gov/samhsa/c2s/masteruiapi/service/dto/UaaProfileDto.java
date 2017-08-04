package gov.samhsa.c2s.masteruiapi.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UaaProfileDto {
    private String email;
    private String familyName;
    private String givenName;
    private String name;
    private String userId;
    private String userName;
}
