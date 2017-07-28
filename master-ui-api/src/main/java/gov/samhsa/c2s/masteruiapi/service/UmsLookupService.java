package gov.samhsa.c2s.masteruiapi.service;

import gov.samhsa.c2s.masteruiapi.infrastructure.dto.RoleDto;

import java.util.List;

public interface UmsLookupService {
    List<RoleDto> getRoles();
}
