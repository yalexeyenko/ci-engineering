package connection;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class SQLConnection {
    public Connection getConnection() {
        InitialContext initialContext;
        try {
            initialContext = new InitialContext();
            DataSource dataSource = (DataSource) initialContext.lookup("java:/comp/env/jdbc/civil-engineering");
            return dataSource.getConnection();
        } catch (NamingException e) {
            throw new SQLConnectionException("InitialContext is wrong");
        } catch (SQLException e) {
            throw new SQLConnectionException("Can't get connection from DataSource");
        }
    }
}
