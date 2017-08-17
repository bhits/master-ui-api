package gov.samhsa.c2s.masteruiapi.service;

import gov.samhsa.c2s.masteruiapi.config.C2sMasterUiProperties;
import gov.samhsa.c2s.masteruiapi.infrastructure.SupportedRoles;
import gov.samhsa.c2s.masteruiapi.service.dto.CredentialsDto;
import gov.samhsa.c2s.masteruiapi.service.dto.LimitedProfileResponse;
import gov.samhsa.c2s.masteruiapi.service.dto.LoginResponseDto;
import gov.samhsa.c2s.masteruiapi.service.dto.UaaTokenDto;
import gov.samhsa.c2s.masteruiapi.service.dto.UaaUserInfoDto;
import gov.samhsa.c2s.masteruiapi.service.exception.AccountLockedException;
import gov.samhsa.c2s.masteruiapi.service.exception.BadCredentialsException;
import gov.samhsa.c2s.masteruiapi.service.exception.UserUnauthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

    private static final String BAD_CREDENTIAL_ERROR_MESSAGE = "Bad credentials";
    private static final String ACCOUNT_LOCKED_ERROR_MESSAGE = "Your account has been locked because of too many failed attempts to login";

    @Autowired
    private UaaService uaaService;

    @Autowired
    private UmsService umsService;

    @Autowired
    private C2sMasterUiProperties c2sMasterUiProperties;

    @Override
    public LoginResponseDto login(CredentialsDto credentialsDto) {
        try {
           UaaTokenDto accessToken = uaaService.getAccessTokenUsingPasswordGrant(credentialsDto);
            if (hasAccessScope(accessToken.getScope(), credentialsDto.getRole())) {
                UaaUserInfoDto userInfo = uaaService.getUserInfo(accessToken);
                // TODO remove this check when staff profile is from UMS
                if (credentialsDto.getRole().equals(SupportedRoles.PATIENT.getName())
                        || credentialsDto.getRole().equals(SupportedRoles.PROVIDER.getName())) {
                    LimitedProfileResponse limitedProfileResponse = umsService.getProfile(userInfo.getUser_id(), userInfo.getUser_name());
                    // Return token to user
                    return LoginResponseDto.builder()
                            .accessToken(accessToken)
                            .limitedProfileResponse(limitedProfileResponse)
                            .c2sClientHomeUrl(getUiHomeUrlByRole(credentialsDto.getRole()))
                            .masterUiLoginUrl(c2sMasterUiProperties.getLoginUrl())
                            .build();
                } else if (credentialsDto.getRole().equals(SupportedRoles.STAFF.getName())) {
                    LimitedProfileResponse limitedProfileResponse = umsService.getStaffProfile();

                    return LoginResponseDto.builder()
                            .accessToken(accessToken)
                            .limitedProfileResponse(limitedProfileResponse)
                            .c2sClientHomeUrl(getUiHomeUrlByRole(credentialsDto.getRole()))
                            .masterUiLoginUrl(c2sMasterUiProperties.getLoginUrl())
                            .build();
                } else {
                    log.error("User role not supported. ");
                }
            } else {
                log.error("User does not have access scope. ");
            }

        }catch (Exception e){
            String errorMessage = e.getCause().getMessage();
            log.error(errorMessage);
            if(errorMessage.contains(BAD_CREDENTIAL_ERROR_MESSAGE)){
                throw new BadCredentialsException();
            }else if(errorMessage.contains(ACCOUNT_LOCKED_ERROR_MESSAGE)){
                throw new AccountLockedException();
            }
        }
        throw new UserUnauthorizedException();
    }

    private String getUiHomeUrlByRole(String role){
        return c2sMasterUiProperties.getMapping().get(role).getHomeUrl();
    }

    private boolean hasAccessScope(String scopes, String selectedRole){
        return scopes.contains(c2sMasterUiProperties.getMapping().get(selectedRole).getAccessScope());
    }
}
