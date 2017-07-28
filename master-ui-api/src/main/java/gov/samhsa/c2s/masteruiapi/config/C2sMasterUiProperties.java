package gov.samhsa.c2s.masteruiapi.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "c2s.master-ui")
@Data
public class C2sMasterUiProperties {

    @NotNull
    private Map<String, C2sRoleProperties > mapping;

    @NotNull
    private String loginUrl;
}
