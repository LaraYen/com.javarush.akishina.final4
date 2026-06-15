package com.javarush.liquibase;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.Scope;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import static com.javarush.AppConfig.DB_SCHEMA;

public class LiquibaseMigrationRunner {

    private static final String CHANGELOG_FILE = "liquibase-migration/changelog-master.xml";

    public void runMigrations() throws Exception {
        Map<String, Object> config = new HashMap<>();

        try (Connection connection = PostgresConnectionData.getConnection()) {
            Scope.child(config, () -> {
                Database database = DatabaseFactory
                        .getInstance()
                        .findCorrectDatabaseImplementation(new JdbcConnection(connection));

                database.setDefaultSchemaName(DB_SCHEMA);
                database.setLiquibaseSchemaName(DB_SCHEMA);

                Liquibase liquibase = new Liquibase(
                        CHANGELOG_FILE,
                        new ClassLoaderResourceAccessor(),
                        database
                );

                liquibase.update(new Contexts(), new LabelExpression());
            });
        }
    }

}