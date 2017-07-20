package gov.samhsa.c2s.masteruiapi.web;

import gov.samhsa.c2s.masteruiapi.infrastructure.dto.RoleDto;
import gov.samhsa.c2s.masteruiapi.service.UmsLookupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("ums/users")
public class UmsLookupRestController {

    @Autowired
    private UmsLookupService umsLookupService;


    @GetMapping("/roles")
    public List<RoleDto> getRoles() {
        return umsLookupService.getRoles();
    }


}