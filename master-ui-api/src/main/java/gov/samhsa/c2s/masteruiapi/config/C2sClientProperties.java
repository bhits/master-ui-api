package gov.samhsa.c2s.masteruiapi.config;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.nio.charset.StandardCharsets;

@Component
@ConfigurationProperties(prefix = "c2s.client")
@Data
public class C2sClientProperties {

    @NotNull
    @Valid
    private C2sUi c2sUi;

    @NotNull
    @Valid
    private ProviderUi providerUi;

    @NotNull
    @Valid
    private StaffUi staffUi;

    @NotNull
    @Valid
    private MasterUi masterUi;

    @Data
    public static class C2sUi{

        @JsonIgnore
        @NotEmpty
        private String clientId;

        @JsonIgnore
        @NotEmpty
        private String clientSecret;

        @NotEmpty
        private String homeUrl;

        public byte[] getBasicAuthorizationHeader() {
            return (clientId.concat(":") + clientSecret).getBytes(StandardCharsets.UTF_8);
        }
    }

    @Data
    public static class ProviderUi{

        @JsonIgnore
        @NotEmpty
        private String clientId;

        @JsonIgnore
        @NotEmpty
        private String clientSecret;

        @NotEmpty
        private String homeUrl;

        public byte[] getBasicAuthorizationHeader() {
            return (clientId.concat(":") + clientSecret).getBytes(StandardCharsets.UTF_8);
        }
    }

    @Data
    public static class StaffUi{

        @JsonIgnore
        @NotEmpty
        private String clientId;

        @JsonIgnore
        @NotEmpty
        private String clientSecret;

        @NotEmpty
        private String homeUrl;

        public byte[] getBasicAuthorizationHeader() {
            return (clientId.concat(":") + clientSecret).getBytes(StandardCharsets.UTF_8);
        }
    }

    @Data
    public static class MasterUi{

        @JsonIgnore
        @NotEmpty
        private String loginUrl;

    }
}
