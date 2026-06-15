package com.javarush.liquibase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class PostgresConnectionData {

    public static final String DB_URL = System.getenv().getOrDefault("DB_URL", "jdbc:postgresql://postgres:5432/world");
    public static final String DB_USER = System.getenv().getOrDefault("POSTGRES_USER", "postgres");
    public static final String DB_PASSWORD = System.getenv().getOrDefault("POSTGRES_PASSWORD", "postgres");

    public static final String DB_SCHEMA = System.getenv().getOrDefault("POSTGRES_DB", "world");

    private PostgresConnectionData() {
    }

    public static Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(
                DB_URL,
                DB_USER,
                DB_PASSWORD
        );

        createSchemaIfNotExists(connection);
        setSearchPath(connection);

        return connection;
    }

    private static void createSchemaIfNotExists(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE SCHEMA IF NOT EXISTS " + DB_SCHEMA);
        }
    }

    private static void setSearchPath(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("SET search_path TO " + DB_SCHEMA);
        }
    }
}