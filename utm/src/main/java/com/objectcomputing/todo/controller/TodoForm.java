package com.objectcomputing.todo;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.serde.annotation.Serdeable;

import javax.validation.constraints.NotBlank;

@Serdeable
@Introspected
public class TodoForm {

    @NonNull
    @NotBlank
    private final String action;

    public TodoForm(@NonNull String action) {
        this.action = action;
    }

    @NonNull
    public String getAction() {
        return action;
    }
}
