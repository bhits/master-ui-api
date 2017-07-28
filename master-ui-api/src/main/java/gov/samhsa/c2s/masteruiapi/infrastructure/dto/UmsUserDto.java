package gov.samhsa.c2s.masteruiapi.infrastructure.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class UmsUserDto {
    private Long id;

    private String userAuthId;

    private String lastName;

    private String middleName;

    private String firstName;

    private LocalDate birthDate;

    private String genderCode;

    private String socialSecurityNumber;

    private List<UmsAddressDto> addresses;

    private List<TelecomDto> telecoms;

    private List<RoleDto> roles;

    private String locale;

    private boolean disabled;

    private String mrn;

    private List<IdentifierDto> identifiers;

    private String registrationPurposeEmail;
}
