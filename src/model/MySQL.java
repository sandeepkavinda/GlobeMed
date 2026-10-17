package model;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class MySQL {

    private static Connection conncetion;

    static {
        try {
             Properties properties = new Properties();

            try (FileInputStream file =
                    new FileInputStream("config/db.properties")) {
                properties.load(file);
            }
            Class.forName("com.mysql.cj.jdbc.Driver");
            conncetion = DriverManager.getConnection(
                    properties.getProperty("db.url"),
                properties.getProperty("db.username"),
                properties.getProperty("db.password")
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ResultSet execute(String query) throws Exception {

        Statement statement = conncetion.createStatement();

        if (query.startsWith("SELECT") | query.startsWith("select")) {
            return statement.executeQuery(query);
        } else {
            statement.executeUpdate(query);
            return null;
        }
    }
}
