package com.objectcomputing.todo;

import io.micronaut.core.annotation.NonNull;

import javax.validation.constraints.NotBlank;
import java.util.List;

public interface TodoRepository {
    @NonNull
    TodoEntity save(String action, String principal);

    @NonNull
    List<Todo> findByPrincipal(@NonNull @NotBlank String name);
}
