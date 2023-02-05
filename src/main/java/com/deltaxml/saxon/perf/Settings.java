package com.deltaxml.saxon.perf;

public class Settings {

    public static boolean overWrite = propertyToBoolean("printer.overWrite", true);
    public static boolean debugPipeline = propertyToBoolean("compare.debug", false);

    private static boolean propertyToBoolean(String propertyName, boolean defaultValue) {
        String propertyValue = System.getProperty(propertyName);
        return propertyValue == null? defaultValue : Boolean.parseBoolean(propertyValue);
    }
}
