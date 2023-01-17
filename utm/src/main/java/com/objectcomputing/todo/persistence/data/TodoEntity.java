package com.objectcomputing.todo;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.annotation.AutoPopulated;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

@MappedEntity("todo")
public class TodoEntity {

    @Id
    @AutoPopulated
    private UUID id;

    @Size(max = 255)
    @NonNull
    @NotBlank
    private final String action;

    @Size(max = 255)
    @NonNull
    @NotBlank
    private final String principal;

    public TodoEntity(@Nullable UUID id,
                      @NonNull String action,
                      @NonNull String principal) {
        this.id = id;
        this.action = action;
        this.principal = principal;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @NonNull
    public String getAction() {
        return action;
    }

    @NonNull
    public String getPrincipal() {
        return principal;
    }
}
