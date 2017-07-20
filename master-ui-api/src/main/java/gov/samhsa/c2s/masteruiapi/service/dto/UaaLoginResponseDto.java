package gov.samhsa.c2s.masteruiapi.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UaaLoginResponseDto {
    private UaaTokenDto uaaTokenDto;
    private  UaaUserInfoDto userInfoDto;
}
