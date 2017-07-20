package gov.samhsa.c2s.masteruiapi.web;

import gov.samhsa.c2s.masteruiapi.service.UaaService;
import gov.samhsa.c2s.masteruiapi.service.dto.CredentialDto;
import gov.samhsa.c2s.masteruiapi.service.dto.UaaLoginResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("uaa/users")
public class UaaRestController {

    @Autowired
    private UaaService uaaService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    public UaaLoginResponseDto loginAndGetUserInfo(@Valid @RequestBody CredentialDto credentialDto   ){
        return uaaService.loginAndGetUserInfo(credentialDto);
    }
}