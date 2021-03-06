package com.example;

import java.util.Optional;

public enum Scenario {
    MAVEN_FUNCTION,
    MAVEN_FUNCTION_NATIVE,
    MAVEN_APP,
    MAVEN_APP_NATIVE,
    GRADLE_APP,
    GRADLE_APP_AOT,
    GRADLE_APP_NATIVE,
    GRADLE_APP_NATIVE_AOT,
    GRADLE_FUNCTION,
    GRADLE_FUNCTION_SERDE,
    GRADLE_FUNCTION_AOT,
    GRADLE_FUNCTION_NATIVE,
    GRADLE_FUNCTION_NATIVE_AOT;

    public static Optional<Scenario> of(String str) {
        //TODO Do this with a map
        if (str == null) {
            return Optional.empty();
        } else if (str.equals("GRADLE_FUNCTION")) {
            return Optional.of(GRADLE_FUNCTION);
        } else if (str.equals("GRADLE_FUNCTION_SERDE")) {
            return Optional.of(GRADLE_FUNCTION_SERDE);
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
        } else if (str.equals("MAVEN_APP")) {
            return Optional.of(MAVEN_APP);
        } else if (str.equals("MAVEN_APP_NATIVE")) {
            return Optional.of(MAVEN_APP_NATIVE);
        } else if (str.equals("MAVEN_FUNCTION")) {
            return Optional.of(MAVEN_FUNCTION);
        } else if (str.equals("MAVEN_FUNCTION_NATIVE")) {
            return Optional.of(MAVEN_FUNCTION_NATIVE);
        }
        return Optional.empty();
    }
}
