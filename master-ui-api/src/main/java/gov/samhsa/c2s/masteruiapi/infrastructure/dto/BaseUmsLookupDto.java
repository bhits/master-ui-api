package gov.samhsa.c2s.masteruiapi.infrastructure.dto;

import lombok.Data;

@Data
public class BaseUmsLookupDto {
    private String code;

    private String displayName;

    private String description;

    private String codeSystem;

    private String codeSystemOID;

    private String codeSystemName;
}
