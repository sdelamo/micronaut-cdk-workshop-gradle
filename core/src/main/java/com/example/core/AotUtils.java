package com.example.core;

public final class AotUtils {

    private AotUtils() {
    }

    public static boolean runningAot(String packageName, ClassLoader classLoader) {
        try {
            Class<?> clazz = Class.forName(packageName + ".AOTApplicationContextConfigurer", false, classLoader);
            if (clazz != null) {
                return true;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
}
