package gov.samhsa.c2s.masteruiapi.web;

import gov.samhsa.c2s.masteruiapi.service.LoginService;
import gov.samhsa.c2s.masteruiapi.service.dto.CredentialsDto;
import gov.samhsa.c2s.masteruiapi.service.dto.LoginResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("c2s")
public class LoginRestController {

    @Autowired
    private LoginService loginService;

    @PostMapping("login")
    @ResponseStatus(HttpStatus.CREATED)
    public LoginResponseDto login(@Valid @RequestBody CredentialsDto credentialsDto){
        return loginService.login(credentialsDto);
    }
}