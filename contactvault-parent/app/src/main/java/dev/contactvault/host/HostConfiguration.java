package dev.contactvault.host;

import io.micronaut.core.annotation.NonNull;

@FunctionalInterface
public interface HostConfiguration {

    @NonNull
    String getFixedHost();
}
