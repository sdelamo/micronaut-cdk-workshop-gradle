package com.objectcomputing.todo;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class DbHolderExtension implements AfterAllCallback, BeforeAllCallback {

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        if (context.getTestInstance().isPresent()) {
            Object testInstance = context.getTestInstance().get();
            if (testInstance instanceof DatabaseTestPropertyProvider) {
                String driverName = ((DatabaseTestPropertyProvider) testInstance).driverName();
                DbHolder.incrementUsedCounter(driverName);
            }
        }
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        if (context.getTestInstance().isPresent()) {
            Object testInstance = context.getTestInstance().get();
            if (testInstance instanceof DatabaseTestPropertyProvider) {
                String driverName = ((DatabaseTestPropertyProvider) testInstance).driverName();
                DbHolder.cleanup(driverName);
            }
        }
    }
}
