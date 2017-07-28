package gov.samhsa.c2s.masteruiapi.infrastructure;

import gov.samhsa.c2s.masteruiapi.infrastructure.dto.RoleDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient("ums")
public interface UmsLookupClient {

    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    List<RoleDto> getRoles();
}
