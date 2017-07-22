package gov.samhsa.c2s.masteruiapi.service;

import gov.samhsa.c2s.masteruiapi.service.dto.LoginResponseDto;
import gov.samhsa.c2s.masteruiapi.service.dto.CredentialsDto;
import gov.samhsa.c2s.masteruiapi.service.dto.UaaTokenDto;
import gov.samhsa.c2s.masteruiapi.service.dto.UaaUserInfoDto;
import gov.samhsa.c2s.masteruiapi.service.dto.LimitedProfileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UaaService uaaService;

    @Autowired
    private UmsService umsService;

    @Override
    public LoginResponseDto login(CredentialsDto credentialsDto) {
        Optional<UaaTokenDto>  accessToken =  uaaService.getAccessTokenUsingPasswordGrant(credentialsDto);
        if(accessToken.isPresent()){
            Optional<UaaUserInfoDto> userInfo = uaaService.getUserInfo(accessToken);
            if(userInfo.isPresent()){
                LimitedProfileResponse limitedProfileResponse = umsService.getProfile(userInfo.get().getUser_id(), userInfo.get().getUser_name());
                // Return token to user
                return LoginResponseDto.builder()
                        .accessToken(accessToken.get())
                        .profile(limitedProfileResponse)
                        .build();
            }else{
                // TODO Throw exception: cannot get user info from UAA
                return null;
            }
        }else {
            // TODO Throw exception : cannot get token from UAA
            return  null;
        }
    }
}
