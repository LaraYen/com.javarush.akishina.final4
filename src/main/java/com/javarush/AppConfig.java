package com.javarush;

public class AppConfig {
    public static final String DB_URL = getEnvOrDefault("DB_URL", "jdbc:postgresql://localhost:5432/world");
    public static final String DB_USER = getEnvOrDefault("DB_USER", "postgres");
    public static final String DB_PASSWORD = getEnvOrDefault("DB_PASSWORD", "postgres");
    public static final String DB_SCHEMA = "world";
    public static final String REDIS_HOST = getEnvOrDefault("REDIS_HOST", "localhost");
    public static final int REDIS_PORT = Integer.parseInt(getEnvOrDefault("REDIS_PORT", "6379"));

    private static String getEnvOrDefault(String name, String defaultValue) {
        String value = System.getenv(name);
        return (value != null && !value.isBlank()) ? value : defaultValue;
    }
}
