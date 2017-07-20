package gov.samhsa.c2s.masteruiapi.service.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CredentialDto {
    private String username;
    private String password;
    private String role;

}
