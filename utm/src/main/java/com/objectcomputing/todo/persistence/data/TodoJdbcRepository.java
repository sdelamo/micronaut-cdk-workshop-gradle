package com.objectcomputing.todo;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.GenericRepository;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.UUID;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface TodoJdbcRepository extends TodoRepository, GenericRepository<TodoEntity, UUID> {
    @Override
    @NonNull
    TodoEntity save(String action, String principal);

    @Override
    @NonNull
    List<Todo> findByPrincipal(@NonNull @NotBlank String name);
}
