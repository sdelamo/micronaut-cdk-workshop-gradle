package iomicronaut.hotwired.demo.controllers;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.uri.UriBuilder;

import java.net.URI;

public class UrlMappings {

    private static final String SLASH = "/";
    private static final String CREATE = "create";

    @NonNull
    public UriBuilder uriBuilder(@NonNull String resource) {
        if (!resource.startsWith(SLASH)) {
            return UriBuilder.of(SLASH + resource);
        }
        return UriBuilder.of(resource);
    }

    @NonNull
    public URI index(@NonNull String resource) {
        return uriBuilder(resource).build();
    }

    @NonNull
    public URI create(@NonNull String resource) {
        return uriBuilder(resource).path(CREATE).build();
    }
}
