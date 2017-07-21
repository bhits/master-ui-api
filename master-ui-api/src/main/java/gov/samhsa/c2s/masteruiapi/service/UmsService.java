package gov.samhsa.c2s.masteruiapi.service;

import gov.samhsa.c2s.masteruiapi.service.dto.FullProfileResponse;
import gov.samhsa.c2s.masteruiapi.service.dto.LimitedProfileResponse;
import gov.samhsa.c2s.masteruiapi.service.dto.UserDto;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Locale;

public interface UmsService {
    FullProfileResponse getFullProfile(String userAuthId);
}
