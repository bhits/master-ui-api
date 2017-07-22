package gov.samhsa.c2s.masteruiapi.service;

import gov.samhsa.c2s.masteruiapi.service.dto.LimitedProfileResponse;

public interface UmsService {
    LimitedProfileResponse getProfile(String userAuthId, String currentUsername);
}
