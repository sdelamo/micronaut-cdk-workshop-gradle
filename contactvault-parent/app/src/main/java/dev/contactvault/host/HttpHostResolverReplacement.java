package dev.contactvault.host;

import io.micronaut.context.annotation.Replaces;
import io.micronaut.context.annotation.Requires;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.server.util.HttpHostResolver;
import jakarta.inject.Singleton;

@Requires(bean = HostConfiguration.class)
@Replaces(HttpHostResolver.class)
@Singleton
public class HttpHostResolverReplacement implements HttpHostResolver {
    private final HostConfiguration hostConfiguration;

    public HttpHostResolverReplacement(HostConfiguration hostConfiguration) {
        this.hostConfiguration = hostConfiguration;
    }

    @Override
    @NonNull
    public String resolve(HttpRequest request) {
        return hostConfiguration.getFixedHost();
    }
}
