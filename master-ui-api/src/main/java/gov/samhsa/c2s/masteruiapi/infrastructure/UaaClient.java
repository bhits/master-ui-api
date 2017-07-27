package gov.samhsa.c2s.masteruiapi.infrastructure;

import feign.Headers;
import gov.samhsa.c2s.masteruiapi.config.CoreFeignConfiguration;
import gov.samhsa.c2s.masteruiapi.service.dto.UaaTokenDto;
import gov.samhsa.c2s.masteruiapi.service.dto.UaaUserInfoDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@FeignClient(name = "uaa", url = "${c2s.authorization-server.host}", path = "/uaa", configuration = CoreFeignConfiguration.class)
public interface UaaClient {

    @RequestMapping(value = "/oauth/token", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    UaaTokenDto getTokenUsingPasswordGrant(Map<String, ?> formParams);

    @RequestMapping(value = "/userinfo", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    UaaUserInfoDto getUserInfo(@RequestHeader("Authorization") String authorizationHeader);
}
