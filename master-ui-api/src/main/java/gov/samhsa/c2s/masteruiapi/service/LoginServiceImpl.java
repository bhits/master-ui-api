package gov.samhsa.c2s.masteruiapi.service;

import gov.samhsa.c2s.masteruiapi.config.C2sMasterUiProperties;
import gov.samhsa.c2s.masteruiapi.infrastructure.SupportedRoles;
import gov.samhsa.c2s.masteruiapi.service.dto.CredentialsDto;
import gov.samhsa.c2s.masteruiapi.service.dto.LimitedProfileResponse;
import gov.samhsa.c2s.masteruiapi.service.dto.LoginResponseDto;
import gov.samhsa.c2s.masteruiapi.service.dto.UaaProfileDto;
import gov.samhsa.c2s.masteruiapi.service.dto.UaaTokenDto;
import gov.samhsa.c2s.masteruiapi.service.dto.UaaUserInfoDto;
import gov.samhsa.c2s.masteruiapi.service.exception.AccountLockedException;
import gov.samhsa.c2s.masteruiapi.service.exception.BadCredentialsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
            Optional<UaaTokenDto> accessToken = uaaService.getAccessTokenUsingPasswordGrant(credentialsDto);
            if (accessToken.isPresent() && hasAccessScope(accessToken.get().getScope(), credentialsDto.getRole())) {
                Optional<UaaUserInfoDto> userInfo = uaaService.getUserInfo(accessToken);
                // TODO remove this check when staff profile is from UMS
                if (userInfo.isPresent() && (credentialsDto.getRole().equals(SupportedRoles.PATIENT.getName())
                        || credentialsDto.getRole().equals(SupportedRoles.PROVIDER.getName()))) {
                    UaaUserInfoDto uaaUserInfo = userInfo.get();
                    LimitedProfileResponse limitedProfileResponse = umsService.getProfile(uaaUserInfo.getUser_id(), uaaUserInfo.getUser_name());
                    // Return token to user
                    return LoginResponseDto.builder()
                            .accessToken(accessToken.get())
                            .limitedProfileResponse(limitedProfileResponse)
                            .c2sClientHomeUrl(getUiHomeUrlByRole(credentialsDto.getRole()))
                            .masterUiLoginUrl(c2sMasterUiProperties.getLoginUrl())
                            .build();
                } else if (userInfo.isPresent() && credentialsDto.getRole().equals(SupportedRoles.STAFF.getName())) {
                    LimitedProfileResponse limitedProfileResponse = umsService.getStaffProfile();

                    return LoginResponseDto.builder()
                            .accessToken(accessToken.get())
                            .limitedProfileResponse(limitedProfileResponse)
                            .c2sClientHomeUrl(getUiHomeUrlByRole(credentialsDto.getRole()))
                            .masterUiLoginUrl(c2sMasterUiProperties.getLoginUrl())
                            .build();
                } else {
                    log.error("User info not present not present ");
                }
            } else {
                log.error("Access token not present ");
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
        return null;
    }

    private String getUiHomeUrlByRole(String role){
        return c2sMasterUiProperties.getMapping().get(role).getHomeUrl();
    }

    private boolean hasAccessScope(String scopes, String selectedRole){
        return scopes.contains(c2sMasterUiProperties.getMapping().get(selectedRole).getAccessScope());
    }

    private UaaProfileDto mapToUaaProfileDto(UaaUserInfoDto uaaUserInfo){
        return UaaProfileDto.builder()
                .email(uaaUserInfo.getEmail())
                .familyName(uaaUserInfo.getFamily_name())
                .givenName(uaaUserInfo.getGiven_name())
                .userId(uaaUserInfo.getUser_id())
                .userName(uaaUserInfo.getUser_name())
                .name(uaaUserInfo.getName()).build();
    }


}
