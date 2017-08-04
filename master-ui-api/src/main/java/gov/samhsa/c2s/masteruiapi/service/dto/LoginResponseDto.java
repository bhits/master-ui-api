package gov.samhsa.c2s.masteruiapi.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponseDto {
    private Object accessToken;
    private UaaProfileDto profileToken;
    private LimitedProfileResponse limitedProfileResponse;
    private String c2sClientHomeUrl;
    private String masterUiLoginUrl;
}
