package gov.samhsa.c2s.masteruiapi.service;

import gov.samhsa.c2s.masteruiapi.service.dto.*;
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
                FullProfileResponse fullProfileResponse = umsService.getFullProfile(userInfo.get().getUser_id());
                // Return token to user
                return LoginResponseDto.builder()
                        .accessToken(accessToken.get())
                        .userInfo(userInfo.get())
                        .profile(fullProfileResponse)
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
