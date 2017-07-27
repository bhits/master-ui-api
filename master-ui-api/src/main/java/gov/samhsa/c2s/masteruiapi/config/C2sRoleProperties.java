package gov.samhsa.c2s.masteruiapi.config;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
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
    @NotEmpty
    private String clientId;

    @JsonIgnore
    @NotEmpty
    private String clientSecret;

    @NotEmpty
    private String accessScope;

    @NotEmpty
    private String homeUrl;
}
