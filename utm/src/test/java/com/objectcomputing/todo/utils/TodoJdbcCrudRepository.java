package com.objectcomputing.todo;

import com.objectcomputing.todo.persistence.data.TodoEntity;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

import java.util.UUID;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface TodoJdbcCrudRepository extends CrudRepository<TodoEntity, UUID> {
}
