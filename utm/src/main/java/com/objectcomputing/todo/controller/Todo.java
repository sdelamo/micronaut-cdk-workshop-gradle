package com.objectcomputing.todo;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.serde.annotation.Serdeable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Introspected
@Serdeable
public class Todo {
    @Size(max = 255)
    @NonNull
    @NotBlank
    private final String action;

    public Todo(@NonNull String action) {
        this.action = action;
    }

    @NonNull
    public String getAction() {
        return action;
    }
}
