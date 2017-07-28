package gov.samhsa.c2s.masteruiapi.service;

import gov.samhsa.c2s.masteruiapi.config.C2sMasterUiProperties;
import gov.samhsa.c2s.masteruiapi.infrastructure.SupportedRoles;
import gov.samhsa.c2s.masteruiapi.service.dto.LoginResponseDto;
import gov.samhsa.c2s.masteruiapi.service.dto.CredentialsDto;
import gov.samhsa.c2s.masteruiapi.service.dto.UaaTokenDto;
import gov.samhsa.c2s.masteruiapi.service.dto.UaaUserInfoDto;
import gov.samhsa.c2s.masteruiapi.service.dto.LimitedProfileResponse;
import gov.samhsa.c2s.masteruiapi.service.exception.UserInforNotPresentException;
import gov.samhsa.c2s.masteruiapi.service.exception.AccessTokenNotPresentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UaaService uaaService;

    @Autowired
    private UmsService umsService;

    @Autowired
    private C2sMasterUiProperties c2sMasterUiProperties;

    @Override
    public LoginResponseDto login(CredentialsDto credentialsDto) {
        Optional<UaaTokenDto>  accessToken =  uaaService.getAccessTokenUsingPasswordGrant(credentialsDto);
        if(accessToken.isPresent() && hasAccessScope(accessToken.get().getScope(), credentialsDto.getRole())){
            Optional<UaaUserInfoDto> userInfo = uaaService.getUserInfo(accessToken);
            // TODO remove this check when staff profile is from UMS
            if(userInfo.isPresent() && (credentialsDto.getRole().equals(SupportedRoles.PATIENT.getName())
                    ||credentialsDto.getRole().equals(SupportedRoles.PROVIDER.getName()) )){

                LimitedProfileResponse limitedProfileResponse = umsService.getProfile(userInfo.get().getUser_id(), userInfo.get().getUser_name());
                // Return token to user
                return LoginResponseDto.builder()
                        .accessToken(accessToken.get())
                        .profileToken(userInfo.get())
                        .limitedProfileResponse(limitedProfileResponse)
                        .c2sClientHomeUrl(getUiHomeUrlByRole(credentialsDto.getRole()))
                        .masterUiLoginUrl(c2sMasterUiProperties.getLoginUrl())
                        .build();
            }else if(userInfo.isPresent() && credentialsDto.getRole().equals(SupportedRoles.STAFF.getName())){
                LimitedProfileResponse limitedProfileResponse = umsService.getStaffProfile();

                return LoginResponseDto.builder()
                        .accessToken(accessToken.get())
                        .profileToken(userInfo.get())
                        .limitedProfileResponse(limitedProfileResponse)
                        .c2sClientHomeUrl(getUiHomeUrlByRole(credentialsDto.getRole()))
                        .masterUiLoginUrl( c2sMasterUiProperties.getLoginUrl())
                        .build();
            }else {
                throw new UserInforNotPresentException("");
            }
        }else {
            throw new AccessTokenNotPresentException();
        }
    }

    private String getUiHomeUrlByRole(String role){
        return c2sMasterUiProperties.getMapping().get(role).getHomeUrl();
    }

    private boolean hasAccessScope(String scopes, String selectedRole){
        return scopes.contains(c2sMasterUiProperties.getMapping().get(selectedRole).getAccessScope());
    }

}
