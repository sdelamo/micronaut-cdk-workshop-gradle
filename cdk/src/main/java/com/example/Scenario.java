package com.example;

import java.util.Optional;

public enum Scenario {
    GRADLE_APP,
    GRADLE_APP_AOT,
    GRADLE_APP_NATIVE,
    GRADLE_APP_NATIVE_AOT,
    GRADLE_FUNCTION,
    GRADLE_FUNCTION_AOT,
    GRADLE_FUNCTION_NATIVE,
    GRADLE_FUNCTION_NATIVE_AOT;

    public static Optional<Scenario> of(String str) {
        if (str == null) {
            return Optional.empty();
        } else if (str.equals("GRADLE_FUNCTION")) {
            return Optional.of(GRADLE_FUNCTION);
        } else if (str.equals("GRADLE_FUNCTION_AOT")) {
            return Optional.of(GRADLE_FUNCTION_AOT);
        } else if (str.equals("GRADLE_FUNCTION_NATIVE")) {
            return Optional.of(GRADLE_FUNCTION_NATIVE);
        } else if (str.equals("GRADLE_FUNCTION_NATIVE_AOT")) {
            return Optional.of(GRADLE_FUNCTION_NATIVE_AOT);
        } else if (str.equals("GRADLE_APP_NATIVE")) {
            return Optional.of(GRADLE_APP_NATIVE);
        } else if (str.equals("GRADLE_APP_NATIVE_AOT")) {
            return Optional.of(GRADLE_APP_NATIVE_AOT);
        } else if (str.equals("GRADLE_APP")) {
            return Optional.of(GRADLE_APP);
        } else if (str.equals("GRADLE_APP_AOT")) {
            return Optional.of(GRADLE_APP_AOT);
        }
        return Optional.empty();
    }
}
