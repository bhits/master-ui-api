package gov.samhsa.c2s.masteruiapi.service;

import gov.samhsa.c2s.masteruiapi.config.C2sClientProperties;
import gov.samhsa.c2s.masteruiapi.infrastructure.SupportedRoles;
import gov.samhsa.c2s.masteruiapi.infrastructure.UaaClient;
import gov.samhsa.c2s.masteruiapi.service.dto.CredentialsDto;
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
    private C2sClientProperties c2sClientProperties;
    private final String OAUTH2_GRAND_TYPE = "password";
    private final String OAUTH2_RESPONSE_TYPE = "token";


    @Autowired
    private UaaClient uaaClient;

    @Override
    public Optional<UaaTokenDto> getAccessTokenUsingPasswordGrant(CredentialsDto credentialsDto) {
        String clientId = null;
        String clientSecret = null;
        String role = credentialsDto.getRole();

        if(role.equals( SupportedRoles.PATIENT.getName())){
            C2sClientProperties.C2sUi c2sUi = c2sClientProperties.getC2sUi();
            clientId = c2sUi.getClientId();
            clientSecret = c2sUi.getClientSecret();
        }else if(role.equals( SupportedRoles.PROVIDER.getName())){
            C2sClientProperties.ProviderUi providerUi = c2sClientProperties.getProviderUi();
            clientId = providerUi.getClientId();
            clientSecret = providerUi.getClientSecret();
        }else if(role.equals( SupportedRoles.STAFF_USER.getName())){
            C2sClientProperties.StaffUi staffUi = c2sClientProperties.getStaffUi();
            clientId = staffUi.getClientId();
            clientSecret = staffUi.getClientSecret();
        }

        if(clientId == null || clientSecret == null){
            // Throw error
        }

        Map<String,String> formParams = createPasswordGrantFormParams(clientId,clientSecret, credentialsDto.getUsername(), credentialsDto.getPassword());
        return Optional.of(uaaClient.getTokenUsingPasswordGrant(formParams));
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
    public Optional<UaaUserInfoDto> getUserInfo(Optional<UaaTokenDto> token) {
        UaaTokenDto uaaTokenDto = token.get();
        String bearerToken = uaaTokenDto.getToken_type().concat(" ").concat(uaaTokenDto.getAccess_token());
        return Optional.of(uaaClient.getUserInfo(bearerToken));
    }
}
