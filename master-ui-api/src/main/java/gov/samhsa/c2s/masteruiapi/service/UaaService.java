package gov.samhsa.c2s.masteruiapi.service;

import gov.samhsa.c2s.masteruiapi.service.dto.CredentialDto;
import gov.samhsa.c2s.masteruiapi.service.dto.UaaLoginResponseDto;
import gov.samhsa.c2s.masteruiapi.service.dto.UaaTokenDto;
import gov.samhsa.c2s.masteruiapi.service.dto.UaaUserInfoDto;

public interface UaaService {
    UaaTokenDto getTokenUsingPasswordGrant(String role, String username, String password);

    UaaUserInfoDto getUserInfo(String bearerToken);

    UaaLoginResponseDto loginAndGetUserInfo(CredentialDto credentialDto);
}
