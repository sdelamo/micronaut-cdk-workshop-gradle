package com.objectcomputing.todo;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.BlockingHttpClient;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

@MicronautTest
public class FrontEndTest {

    @Inject
    @Client("/")
    HttpClient httpClient;

    @Test
    void apexPathServesFrontEnd() {
        BlockingHttpClient client = httpClient.toBlocking();
        String html = client.retrieve(HttpRequest.GET("/"));
        assertTrue(html.contains("bootstrap.min.css"));
    }
}
