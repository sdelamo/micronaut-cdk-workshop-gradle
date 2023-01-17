package com.objectcomputing.todo;

import com.objectcomputing.todo.controller.Todo;
import io.micronaut.context.annotation.Property;
import io.micronaut.context.annotation.Replaces;
import io.micronaut.context.annotation.Requires;
import io.micronaut.core.type.Argument;
import io.micronaut.core.util.StringUtils;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.BlockingHttpClient;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.security.authentication.PrincipalArgumentBinder;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.micronaut.test.testcontainers.data.DbHolderExtension;
import io.micronaut.test.testcontainers.data.postgres.PostgresTestPropertyProvider;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Property(name = "micronaut.security.filter.enabled", value = StringUtils.FALSE)
@Property(name = "spec.name", value = "TodoControllerTest")
@MicronautTest
@ExtendWith({ DbHolderExtension.class })
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TodoControllerTest implements PostgresTestPropertyProvider {

    @Inject
    @Client("/")
    HttpClient httpClient;

    @Inject
    TodoJdbcCrudRepository todoJdbcCrudRepository;

    @Test
    void getTodoRetrievesEveryTodoOfAUser() {
        BlockingHttpClient client = httpClient.toBlocking();
        List<Todo> todos = client.retrieve(HttpRequest.GET("/todo"), Argument.listOf(Todo.class));
        assertNotNull(todos);
        assertEquals(0, todos.size());
        HttpResponse<?> response = client.exchange(HttpRequest.POST("/todo", Collections.singletonMap("action", "setup jacoco")));
        assertEquals(HttpStatus.CREATED, response.getStatus());
        todos = client.retrieve(HttpRequest.GET("/todo"), Argument.listOf(Todo.class));
        assertNotNull(todos);
        assertEquals(1, todos.size());
        todoJdbcCrudRepository.deleteAll();
    }

    @Requires(property = "spec.name", value = "TodoControllerTest")
    @Replaces(PrincipalArgumentBinder.class)
    @Singleton
    static class PrincipalBinder extends PrincipalArgumentBinderReplacement {
        public PrincipalBinder() {
            super("sergio");
        }
    }
}