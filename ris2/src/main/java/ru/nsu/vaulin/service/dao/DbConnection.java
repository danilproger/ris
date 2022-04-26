package ru.nsu.vaulin.service.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;
import java.util.stream.Collectors;

public class DbConnection {
    private static final Logger logger = LoggerFactory.getLogger(DbConnection.class);

    private static final DbConnection instance = new DbConnection();

    private static final String DB_STRUCTURE_PATH = "initial_structure.sql";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/database_osm";
    private static final String DB_USERNAME = "database_osm";
    private static final String DB_PASSWORD = "1";

    private static Connection connection;

    private DbConnection() {
    }

    public void connect() throws Exception {
        Class.forName("org.postgresql.Driver");
        Locale.setDefault(Locale.US);
        connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        connection.setAutoCommit(false);
    }

    public void initDatabase() throws Exception {
        InputStream dbStructureStream = ClassLoader.getSystemClassLoader().getResourceAsStream(DB_STRUCTURE_PATH);
        String dbStructureSql = new BufferedReader(new InputStreamReader(dbStructureStream))
                .lines().collect(Collectors.joining("\n"));

        connection.createStatement().execute(dbStructureSql);
        connection.commit();
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static DbConnection instance() {
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }


}
