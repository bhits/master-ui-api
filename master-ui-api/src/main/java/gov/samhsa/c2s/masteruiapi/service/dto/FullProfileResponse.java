package gov.samhsa.c2s.masteruiapi.service.dto;

import gov.samhsa.c2s.masteruiapi.infrastructure.dto.BaseUmsLookupDto;
import gov.samhsa.c2s.masteruiapi.infrastructure.dto.IdentifierDto;
import gov.samhsa.c2s.masteruiapi.infrastructure.dto.UmsAddressDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FullProfileResponse {
    private Long userId;
    private String userAuthId;
    private String userLocale;
    private List<BaseUmsLookupDto> supportedLocales;
    private String lastName;
    private String firstName;
    private String middleName;
    private String genderCode;
    private String mrn;
    private LocalDate birthDate;
    private String socialSecurityNumber;
    private UmsAddressDto homeAddress;
    private String homeEmail;
    private String homePhone;
    private List<String> roles;
    private List<IdentifierDto> identifiers;
    private String registrationPurposeEmail;
}
