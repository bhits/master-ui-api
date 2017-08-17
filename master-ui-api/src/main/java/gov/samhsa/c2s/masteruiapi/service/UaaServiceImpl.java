package gov.samhsa.c2s.masteruiapi.service;

import gov.samhsa.c2s.masteruiapi.config.C2sMasterUiProperties;
import gov.samhsa.c2s.masteruiapi.config.C2sRoleProperties;
import gov.samhsa.c2s.masteruiapi.infrastructure.UaaClient;
import gov.samhsa.c2s.masteruiapi.service.dto.CredentialsDto;
import gov.samhsa.c2s.masteruiapi.service.dto.UaaTokenDto;
import gov.samhsa.c2s.masteruiapi.service.dto.UaaUserInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UaaServiceImpl implements UaaService {


    private final String OAUTH2_GRAND_TYPE = "password";
    private final String OAUTH2_RESPONSE_TYPE = "token";

    @Autowired
    private C2sMasterUiProperties c2sMasterUiProperties;

    @Autowired
    private UaaClient uaaClient;

    @Override
    public UaaTokenDto getAccessTokenUsingPasswordGrant(CredentialsDto credentialsDto) {
        C2sRoleProperties c2sRoleProperties =  c2sMasterUiProperties.getMapping().get(credentialsDto.getRole());
        Map<String,String> formParams = createPasswordGrantFormParams(c2sRoleProperties.getClientId(),c2sRoleProperties.getClientSecret(), credentialsDto.getUsername(), credentialsDto.getPassword());
        return uaaClient.getTokenUsingPasswordGrant(formParams);
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
    public UaaUserInfoDto getUserInfo(UaaTokenDto token) {
        String bearerToken = token.getToken_type().concat(" ").concat(token.getAccess_token());
        return uaaClient.getUserInfo(bearerToken);
    }
}
