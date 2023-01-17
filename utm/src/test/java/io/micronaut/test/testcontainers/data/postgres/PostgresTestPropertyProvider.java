package com.objectcomputing.todo;

import io.micronaut.data.model.query.builder.sql.Dialect;
import org.testcontainers.containers.JdbcDatabaseContainer;

interface PostgresTestPropertyProvider extends SharedDatabaseContainerTestPropertyProvider {

    @Override
    default Dialect dialect() {
        return Dialect.POSTGRES;
    }

    @Override
    default void startContainer(JdbcDatabaseContainer container) {
        container.start();
    }
}

