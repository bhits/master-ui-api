package gov.samhsa.c2s.masteruiapi.config;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.nio.charset.StandardCharsets;

@ConfigurationProperties(prefix = "c2s.master-ui.mapping")
@Data
public class C2sRoleProperties {

    @JsonIgnore
    @NotBlank
    private String clientId;

    @JsonIgnore
    @NotBlank
    private String clientSecret;

    @NotBlank
    private String accessScope;

    @NotBlank
    private String homeUrl;
}
