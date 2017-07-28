package gov.samhsa.c2s.masteruiapi.infrastructure;

import gov.samhsa.c2s.masteruiapi.infrastructure.dto.BaseUmsLookupDto;
import gov.samhsa.c2s.masteruiapi.infrastructure.dto.UmsUserDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("ums")
public interface UmsClient {
    @RequestMapping(value = "/locales", method = RequestMethod.GET)
    List<BaseUmsLookupDto> getLocales();

    @RequestMapping(value = "/users/authId/{userAuthId}", method = RequestMethod.GET)
    UmsUserDto getUserByAuthId(@PathVariable("userAuthId") String userAuthId);

}