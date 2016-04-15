package connection;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DBConnection {
    public static Connection getConnection() {
        InitialContext initialContext;
        DataSource dataSource;

        try {
            initialContext = new InitialContext();
            dataSource = (DataSource) initialContext.lookup("java:/comp/env/jdbc/ci-engineering");
            return dataSource.getConnection();
        } catch (NamingException e) {
            throw new DBConnectionException("InitialContext error", e);
        } catch (SQLException e) {
            throw new DBConnectionException("SQL error", e);
        }
    }
}
