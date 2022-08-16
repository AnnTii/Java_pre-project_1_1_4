package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {

    private static final String userName = "root";
    private static final String password = "1234";
    private static final String url = "jdbc:mysql://localhost:3306/preproject";
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static Connection connection;
    private static SessionFactory sessionFactory;

    public static Connection getConnection() {
        try {
            connection = DriverManager.getConnection(url, userName, password);
            connection.setAutoCommit(false);
            Class.forName(driver);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                Properties properties = new Properties();

                properties.put(Environment.USER, "root");
                properties.put(Environment.PASS, "1234");
                properties.put(Environment.URL, "jdbc:mysql://localhost:3306/preproject");
                properties.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                properties.put(Environment.SHOW_SQL, "true");
                properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
                properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                properties.put(Environment.AUTOCOMMIT, "false");

                configuration.setProperties(properties);
                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
                System.out.println("Connected hibernate");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } return sessionFactory;
    }

    public static void main(String[] args) {

    }
}
