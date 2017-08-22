package gov.samhsa.c2s.masteruiapi.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UaaUserInfoDto {
    private String email;
    private String family_name;
    private String given_name;
    private String name;
    private String user_id;
    private String user_name;
}
