package com.objectcomputing.todo;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.util.CollectionUtils;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.runtime.config.SchemaGenerate;
import io.micronaut.test.support.TestPropertyProvider;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Map;

interface DatabaseTestPropertyProvider extends TestPropertyProvider {

    Dialect dialect();

    default SchemaGenerate schemaGenerate() {
        return SchemaGenerate.CREATE;
    }

    default String driverName() {
        switch (dialect()) {
            case POSTGRES:
                return "postgresql";
            case SQL_SERVER:
                return "sqlserver";
            case ORACLE:
                return "oracle";
            case MYSQL:
//                return "mariadb";
                return "mysql";
            case H2:
            default:
                return "h2";
        }
    }

    default String jdbcUrl(JdbcDatabaseContainer container) {
        return container.getJdbcUrl();
    }

    default JdbcDatabaseContainer getDatabaseContainer(String driverName) {
        switch (driverName) {
            case "postgresql":
            default:
                return new PostgreSQLContainer<>("postgres:10");
        }
    }

    @Override
    @NonNull
    default Map<String, String> getProperties() {
        Dialect dialect = dialect();
        String driverName = driverName();
        JdbcDatabaseContainer container = getDatabaseContainer(driverName);
        if (container != null && !container.isRunning()) {
            startContainer(container);
        }
        return CollectionUtils.mapOf(
                "datasources.default.url", jdbcUrl(container),
                "datasources.default.username",  container == null ? "" : container.getUsername(),
                "datasources.default.password",  container == null ? "" : container.getPassword(),
                "datasources.default.schema-generate",  schemaGenerate(),
                "datasources.default.dialect",  dialect);
    }

    default void startContainer(JdbcDatabaseContainer container) {
        container.start();
    }
}