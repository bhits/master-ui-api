package gov.samhsa.c2s.masteruiapi.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UaaTokenDto {
    private String access_token;
    private int expires_in;
    private String jti;
    private String refresh_token;
    private String scope;
    private String token_type;
}
