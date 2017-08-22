package gov.samhsa.c2s.masteruiapi.service;

import gov.samhsa.c2s.masteruiapi.service.dto.CredentialsDto;
import gov.samhsa.c2s.masteruiapi.service.dto.UaaTokenDto;
import gov.samhsa.c2s.masteruiapi.service.dto.UaaUserInfoDto;

public interface UaaService {
    UaaTokenDto getAccessTokenUsingPasswordGrant(CredentialsDto credentialsDto);

    UaaUserInfoDto getUserInfo(UaaTokenDto uaaTokenDto);
}
