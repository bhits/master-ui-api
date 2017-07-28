package gov.samhsa.c2s.masteruiapi.service;

import gov.samhsa.c2s.masteruiapi.service.dto.CredentialsDto;
import gov.samhsa.c2s.masteruiapi.service.dto.UaaTokenDto;
import gov.samhsa.c2s.masteruiapi.service.dto.UaaUserInfoDto;

import java.util.Optional;

public interface UaaService {
    Optional<UaaTokenDto> getAccessTokenUsingPasswordGrant(CredentialsDto credentialsDto);

    Optional<UaaUserInfoDto> getUserInfo(Optional<UaaTokenDto> uaaTokenDto);
}
