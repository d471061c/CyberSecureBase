package sec.project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CyberSecurityBaseProjectApplication {

    /**
     * Create database if it does not already exist
     */
    public static void initializeDatabase() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:h2:./database", "", "");
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE Signup (\n" +
            "            id INTEGER PRIMARY KEY AUTO_INCREMENT,\n" +
            "            name VARCHAR(255) NOT NULL,\n" +
            "            address VARCHAR(255) NOT NULL\n" +
            "        );");
            statement.close();
            connection.close();
        } catch (Exception ex) {
        }
    }
    
    public static void main(String[] args) throws Throwable {
        initializeDatabase();
        SpringApplication.run(CyberSecurityBaseProjectApplication.class);
    }
}
