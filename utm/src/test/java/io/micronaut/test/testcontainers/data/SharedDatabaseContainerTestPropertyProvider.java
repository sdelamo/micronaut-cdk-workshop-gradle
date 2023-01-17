package com.objectcomputing.todo;

import org.testcontainers.containers.JdbcDatabaseContainer;

interface SharedDatabaseContainerTestPropertyProvider extends DatabaseTestPropertyProvider {

    @Override
    default JdbcDatabaseContainer getDatabaseContainer(String driverName) {
        return DbHolder.getContainerOrCreate(driverName, () -> DatabaseTestPropertyProvider.super.getDatabaseContainer(driverName));
    }
}