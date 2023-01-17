package dev.contactvault.host;

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.context.annotation.Requires;
import io.micronaut.core.annotation.NonNull;

import javax.validation.constraints.NotBlank;

@Requires(property = "host.fixed-host")
@ConfigurationProperties("host")
public class HostConfigurationProperties implements HostConfiguration {
    @NonNull
    @NotBlank
    private String fixedHost;
    @Override
    public String getFixedHost() {
        return fixedHost;
    }

    public void setFixedHost(@NonNull String fixedHost) {
        this.fixedHost = fixedHost;
    }
}
