package com.objectcomputing.todo.controller;

import io.micronaut.core.beans.BeanIntrospection;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class TodoTest {
    @Test
    void classTodoIsAnnotatedWithAtIntrospected() {
        Executable e = () -> BeanIntrospection.getIntrospection(Todo.class);
        assertDoesNotThrow(e);
    }
}
