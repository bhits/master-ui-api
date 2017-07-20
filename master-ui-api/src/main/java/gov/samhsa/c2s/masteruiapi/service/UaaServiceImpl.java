package gov.samhsa.c2s.masteruiapi.service;

import gov.samhsa.c2s.masteruiapi.config.C2sOauth2ClientProperties;
import gov.samhsa.c2s.masteruiapi.infrastructure.SupportedRoles;
import gov.samhsa.c2s.masteruiapi.infrastructure.UaaClient;
import gov.samhsa.c2s.masteruiapi.service.dto.CredentialDto;
import gov.samhsa.c2s.masteruiapi.service.dto.UaaLoginResponseDto;
import gov.samhsa.c2s.masteruiapi.service.dto.UaaTokenDto;
import gov.samhsa.c2s.masteruiapi.service.dto.UaaUserInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UaaServiceImpl implements UaaService {

    @Autowired
    private C2sOauth2ClientProperties c2sOauth2ClientProperties;
    private final String OAUTH2_GRAND_TYPE = "password";
    private final String OAUTH2_RESPONSE_TYPE = "token";


    @Autowired
    private UaaClient uaaClient;

    @Override
    public UaaTokenDto getTokenUsingPasswordGrant(String role, String username, String password) {
        C2sOauth2ClientProperties.Client client = c2sOauth2ClientProperties.getClient();
        String clientId = null;
        String clientSecret = null;

        if(role.equals( SupportedRoles.PATIENT.getName())){
            C2sOauth2ClientProperties.Client.C2sUi c2sUi = client.getC2sUi();
            clientId = c2sUi.getClientId();
            clientSecret = c2sUi.getClientSecret();
        }else if(role.equals( SupportedRoles.PROVIDER.getName())){
            C2sOauth2ClientProperties.Client.ProviderUi providerUi = client.getProviderUi();
            clientId = providerUi.getClientId();
            clientSecret = providerUi.getClientSecret();
        }else if(role.equals( SupportedRoles.STAFF_USER.getName())){
            C2sOauth2ClientProperties.Client.StaffUi staffUi = client.getStaffUi();
            clientId = staffUi.getClientId();
            clientSecret = staffUi.getClientSecret();
        }

        if(clientId == null || clientSecret == null){
            // Throw error
        }

        return uaaClient.getTokenUsingPasswordGrant(createPasswordGrantFormParams(clientId,clientSecret, username, password));
    }

    private Map<String,String> createPasswordGrantFormParams(String clientId, String clientSecret, String username, String password){
        Map<String,String> formParams =  new HashMap<>();
        formParams.put("client_id", clientId);
        formParams.put("client_secret", clientSecret);
        formParams.put("grant_type", OAUTH2_GRAND_TYPE);
        formParams.put("response_type", OAUTH2_RESPONSE_TYPE);
        formParams.put("username", username);
        formParams.put("password", password);

        return formParams;
    }
    @Override
    public UaaUserInfoDto getUserInfo(String token) {
        return uaaClient.getUserInfo(token);
    }

    @Override
    public UaaLoginResponseDto loginAndGetUserInfo(CredentialDto credentialDto) {

        Optional<UaaTokenDto> token = Optional.of(getTokenUsingPasswordGrant(credentialDto.getRole(), credentialDto.getUsername(), credentialDto.getPassword()));

        if(token.isPresent()){
            UaaTokenDto uaaTokenDto = token.get();
            String bearerToken = uaaTokenDto.getToken_type().concat(" ").concat(uaaTokenDto.getAccess_token());
            Optional<UaaUserInfoDto> userInfo = Optional.of(getUserInfo(bearerToken));
            if(userInfo.isPresent()){
                // Return token to user
                return UaaLoginResponseDto.builder()
                        .uaaTokenDto(uaaTokenDto)
                        .userInfoDto(userInfo.get())
                        .build();
            }else{
                // Throw exception and log cannot get userinfo
            }
        }else{
            // Throw exception and  log cannot get access token error
        }
        return null;
    }
}
