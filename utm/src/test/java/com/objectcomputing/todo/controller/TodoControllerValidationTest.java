package com.objectcomputing.todo;

import com.objectcomputing.todo.controller.Todo;
import com.objectcomputing.todo.persistence.TodoRepository;
import com.objectcomputing.todo.persistence.data.TodoEntity;
import io.micronaut.context.annotation.Property;
import io.micronaut.context.annotation.Replaces;
import io.micronaut.context.annotation.Requires;
import io.micronaut.core.util.StringUtils;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.BlockingHttpClient;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.security.authentication.PrincipalArgumentBinder;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Property(name = "micronaut.security.filter.enabled", value = StringUtils.FALSE)
@Property(name = "spec.name", value = "TodoControllerValidationTest")
@MicronautTest
public class TodoControllerValidationTest {

    @Inject
    @Client("/")
    HttpClient httpClient;

    @Test
    void blankActionThrowsBadRequest() {
        BlockingHttpClient client = httpClient.toBlocking();
        Executable e = () -> client.exchange(HttpRequest.POST("/todo", Collections.singletonMap("action", "")));
        HttpClientResponseException thrown = assertThrows(HttpClientResponseException.class, e);
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatus());
    }

    @Requires(property = "spec.name", value = "TodoControllerValidationTest")
    @Singleton
    @Replaces(TodoRepository.class)
    static class TodoRepositoryReplacement implements TodoRepository {

        @Override
        public TodoEntity save(String action, String principal) {
            return new TodoEntity(UUID.randomUUID(), action, principal);
        }

        @Override
        public List<Todo> findByPrincipal(String name) {
            return Collections.emptyList();
        }
    }

    @Requires(property = "spec.name", value = "TodoControllerValidationTest")
    @Replaces(PrincipalArgumentBinder.class)
    @Singleton
    static class PrincipalBinder extends PrincipalArgumentBinderReplacement {
        public PrincipalBinder() {
            super("sergio");
        }
    }
}
