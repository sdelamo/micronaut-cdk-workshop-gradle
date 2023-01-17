package com.objectcomputing.todo;

import org.testcontainers.containers.JdbcDatabaseContainer;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

class DbHolder {
    private static Map<String, JdbcDatabaseContainer> dbs = new HashMap<>();
    private static Map<String, AtomicInteger> usedCounter = new HashMap<>();

    static <T> T getContainerOrCreate(String dbName, Supplier<T> create) {
        return (T) dbs.computeIfAbsent(dbName, s -> (JdbcDatabaseContainer) create.get());
    }

    static void incrementUsedCounter(String dbName) {
        int count = usedCounter.computeIfAbsent(dbName, x -> new AtomicInteger(0)).incrementAndGet();
        System.out.println("counter for db " + dbName + " is " + count);
    }

    static void cleanup(String dbName) {
        int value = usedCounter.computeIfAbsent(dbName, x -> new AtomicInteger(0)).decrementAndGet();
        System.out.println("counter for db " + dbName + " is " + value);
        if (value == 0) {
            dbs.remove(dbName).stop();
        }
    }
}

