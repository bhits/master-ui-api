package gov.samhsa.c2s.masteruiapi.service;

import gov.samhsa.c2s.masteruiapi.infrastructure.SupportedRoles;
import gov.samhsa.c2s.masteruiapi.infrastructure.UmsLookupClient;
import gov.samhsa.c2s.masteruiapi.infrastructure.dto.RoleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UmsLookupServiceImpl implements UmsLookupService {

    @Autowired
    private UmsLookupClient umsLookupClient;


    @Override
    public List<RoleDto> getRoles() {
        // Filter return roles by currently supported user in the UI
        return umsLookupClient.getRoles().stream()
                .filter(roleDto -> roleDto.getCode().equals(SupportedRoles.PROVIDER.getName()) ||
                                   roleDto.getCode().equals(SupportedRoles.PATIENT.getName()) ||
                                   roleDto.getCode().equals(SupportedRoles.STAFF_USER.getName()) )
                .collect(Collectors.toList());

    }
}
