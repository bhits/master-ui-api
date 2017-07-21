package gov.samhsa.c2s.masteruiapi.service;

import gov.samhsa.c2s.masteruiapi.service.dto.CredentialsDto;
import gov.samhsa.c2s.masteruiapi.service.dto.LoginResponseDto;

public interface LoginService {
    LoginResponseDto login(CredentialsDto credentialsDto);
}
