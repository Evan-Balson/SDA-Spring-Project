package com.stockshark.config;

import io.github.cdimascio.dotenv.Dotenv;

public class DatabaseConfig {
    private static final Dotenv dotenv = Dotenv.load();

    public static String getHost() {
        return dotenv.get("MYSQL_HOST");
    }

    public static String getUser() {
        return dotenv.get("MYSQL_ROOT_USER");
    }

    public static String getPassword() {
        return dotenv.get("MYSQL_ROOT_PASSWORD");
    }

    public static String getDatabaseName() {
        return dotenv.get("MYSQL_DATABASE");
    }

    public static int getPort() {
        try {
            return Integer.parseInt(dotenv.get("DB_PORT"));
        } catch (NumberFormatException e) {
            // Default MySQL port
            return 3306;
        }
    }
}
